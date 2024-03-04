package cevas.backend.service;



import cevas.backend.domain.Authority;
import cevas.backend.domain.Member;
import cevas.backend.domain.RefreshToken;
import cevas.backend.dto.Token;
import cevas.backend.dto.request.LoginRequest;
import cevas.backend.dto.request.SignupRequest;
import cevas.backend.dto.request.TokenRequest;
import cevas.backend.dto.response.SignupResponse;
import cevas.backend.exception.CustomException;
import cevas.backend.exception.ErrorInfo;
import cevas.backend.jwt.JwtProvider;
import cevas.backend.repository.MemberRepository;
import cevas.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signup(SignupRequest signupRequest) {
        if (memberRepository.existsByEmail(signupRequest.getEmail())) {
            throw new CustomException(ErrorInfo.EMAIL_ALREADY_EXIST);
        }
        else if (memberRepository.existsByNickname(signupRequest.getNickname())){
            throw new CustomException(ErrorInfo.NICKNAME_ALREADY_EXIST);
        }

        Member member = signupRequest.createMember(passwordEncoder);
        return memberRepository.save(member);
    }

    /* 회원가입 시, 유효성 체크 */
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public Token login(LoginRequest loginRequest) {
        // 로그인 credentials을 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
        log.info(authenticationToken.getCredentials().toString());
        // 사용자 비밀번호 체크
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); // causes 403 error
        // 인증 정보 기반으로 JWT 토큰 생성
        Token tokenDto = jwtProvider.generateToken(authentication);

        // refresh token 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 토큰 발급 ~~
        return tokenDto;
    }

    // 토큰 재발급
    public Token reissue(TokenRequest tokenRequest) {
        // refresh token 검증
        if (!jwtProvider.validateToken(tokenRequest.getRefreshToken())) {
            throw new CustomException(ErrorInfo.INVALID_REFRESH_TOKEN);
        }

        // access token에서 memberID 가져오기
        Authentication authentication = jwtProvider.getAuthentication(tokenRequest.getAccessToken());

        // 저장소에서 memberID 기반으로 refresh token 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorInfo.LOGOUT));

        // refresh token 일치 확인
        if (!refreshToken.getValue().equals(tokenRequest.getRefreshToken())) {
            throw new CustomException(ErrorInfo.INVALID_TOKEN_CREDENTIALS);
        }

        // 새로운 토큰 생성
        Token tokenDto = jwtProvider.generateToken(authentication);

        // 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }



}
