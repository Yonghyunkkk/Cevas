package cevas.backend.dto.response;

import cevas.backend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private String email;
    private String nickname;
    private String password;
    private String admissionYear;
    private String major;

    public static  MemberResponse of(Member member) {
        return new MemberResponse(member.getEmail(), member.getNickname(), member.getPassword(), member.getAdmissionYear(), member.getMajor());
    }
}
