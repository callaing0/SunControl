package com.suncontrol.domain.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeForm {

    // 현재 비밀번호
    @NotBlank
    private String currentPassword;
    // 새 비밀번호
    @NotBlank
    private String newPassword;
    // 새 비밀번호 확인
    @NotBlank
    private String confirmPassword;
}
