package cevas.backend.dto.request;

import cevas.backend.domain.Authority;
import cevas.backend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String email;
    private String nickname;
    private String password;
    private String admissionYear;
    private String major;

    public Member createMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .admissionYear(admissionYear)
                .major(major)
                .authority(Authority.ROLE_USER)
                .build();
    }

}
