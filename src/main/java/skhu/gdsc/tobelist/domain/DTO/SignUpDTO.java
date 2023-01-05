package skhu.gdsc.tobelist.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
public class SignUpDTO {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=2, message = "닉네임은 2글자 이상 사용 가능합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호를 입력해주세요")
    // 비밀번호 정규식
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$",
            message = "8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    private String checkedPassword;     // 비밀번호 일치 확인

}
