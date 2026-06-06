package com.iov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class ArticleUpdateDTO {
    @NotNull
    private Integer id;
    @NotEmpty
    @Pattern(regexp =  "^.{1,120}$")
    private String title;
    @NotEmpty
    private String content;
//    @NotEmpty
    @URL
    private String coverImg;
    @NotEmpty
    @Pattern(regexp = "^(公開|下書き)$")
    private String state;//发布状态 已发布|草稿
    @NotNull
    private Integer categoryId;
}
