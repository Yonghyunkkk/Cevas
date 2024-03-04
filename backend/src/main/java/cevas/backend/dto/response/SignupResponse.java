package cevas.backend.dto.response;

import cevas.backend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponse {
    private String email;
    private String nickname;
    private String admissionYear;
    private String major;

    public static SignupResponse of(Member member) {
        return new SignupResponse(member.getEmail(), member.getNickname(), member.getAdmissionYear(), member.getMajor());
    }
}
