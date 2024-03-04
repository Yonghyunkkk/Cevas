package cevas.backend.controller;

import cevas.backend.domain.Member;
import cevas.backend.dto.Token;
import cevas.backend.dto.request.LoginRequest;
import cevas.backend.dto.request.SignupRequest;
import cevas.backend.dto.request.TokenRequest;
import cevas.backend.dto.response.SignupResponse;
import cevas.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(@Valid @RequestBody SignupRequest signupRequest, Errors errors) {
        if (errors.hasErrors()) {
            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = authService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                throw new IllegalStateException(key);
            }
        }
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/reissue")
    public ResponseEntity<Token> reissue(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.reissue(tokenRequest));
    }
}
