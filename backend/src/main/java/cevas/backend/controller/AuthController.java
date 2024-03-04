package cevas.backend.controller;

import cevas.backend.domain.Member;
import cevas.backend.dto.Token;
import cevas.backend.dto.request.LoginRequest;
import cevas.backend.dto.request.MemberRequest;
import cevas.backend.dto.request.TokenRequest;
import cevas.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "1. Authorization")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create user account")
    public ResponseEntity<Member> signup(@Valid @RequestBody MemberRequest memberRequest, Errors errors) {
        if (errors.hasErrors()) {
            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = authService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                throw new IllegalStateException(key);
            }
        }
        return ResponseEntity.ok(authService.signup(memberRequest));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "user login")
    public ResponseEntity<Token> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "reissue access token ")
    public ResponseEntity<Token> reissue(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.reissue(tokenRequest));
    }
}
