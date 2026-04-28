package com.iov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserPwdUpdateDTO {
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String oldPwd;
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String newPwd;
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String rePwd;
}
