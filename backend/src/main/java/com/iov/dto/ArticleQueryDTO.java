package com.iov.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ArticleQueryDTO {
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    private Integer categoryId;
    @Pattern(regexp = "^(公開|下書き)$")
    private String state;
}
