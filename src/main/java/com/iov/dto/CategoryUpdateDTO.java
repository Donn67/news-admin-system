package com.iov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryUpdateDTO {
    @NotNull
    private Integer id;
    @NotBlank
    private String categoryName;
    @NotEmpty
    private String categoryAlias;
}
