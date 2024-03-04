package cevas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwRequest {
    private String oldPw;

    @NotBlank(message = "password must not be left blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,}$", message = "Password must be of 8-16 characters long, with at least 1 alphabet, at least 1 number and 1 special character.")
    private String newPw;
}
