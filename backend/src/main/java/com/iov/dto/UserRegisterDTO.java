package com.iov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String password;
}