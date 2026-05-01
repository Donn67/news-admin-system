package com.iov.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategorySaveDTO {
    @NotBlank
    private String categoryName;
    @NotEmpty
    private String categoryAlias;
}
