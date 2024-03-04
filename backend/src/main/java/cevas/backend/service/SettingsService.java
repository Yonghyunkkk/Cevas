package cevas.backend.service;


import cevas.backend.domain.Member;
import cevas.backend.dto.request.MemberRequest;
import cevas.backend.dto.response.MemberResponse;
import cevas.backend.exception.CustomException;
import cevas.backend.exception.ErrorInfo;
import cevas.backend.repository.MemberRepository;
import cevas.backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettingsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponse::of)
                .orElseThrow(() -> new CustomException(ErrorInfo.MEMBER_NOT_FOUND));
    }

    @Transactional
    public void changeMemberDetails(MemberRequest memberRequest) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new CustomException(ErrorInfo.MEMBER_NOT_FOUND));

        member.updateMember(memberRequest.getEmail(), memberRequest.getNickname(), memberRequest.getAdmissionYear(), memberRequest.getMajor());
    }

    @Transactional
    public void changeMemberPassword(String oldPw, String newPw) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new CustomException(ErrorInfo.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(oldPw, member.getPassword())) {
            throw new CustomException(ErrorInfo.WRONG_PASSWORD);
        }
        member.changePw(passwordEncoder.encode(newPw));
    }
}
