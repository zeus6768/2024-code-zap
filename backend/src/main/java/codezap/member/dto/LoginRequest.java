package codezap.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
        @Schema(description = "이메일", example = "code@zap.com")
        @Email(message = "이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일이 입력되지 않았습니다.")
        @Size(max = 255, message = "이메일은 255자 이하로 입력해주세요.")
        String email,

        @Schema(description = "비밀번호. 영어와 숫자를 반드시 포함해야 합니다.", example = "password1234")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "영어와 숫자를 포함해야합니다.")
        @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
        @Size(min = 8, max = 255, message = "비밀번호는 8~16자 사이로 입력해주세요.")
        String password
) {
}
