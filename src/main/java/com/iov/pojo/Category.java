package com.iov.pojo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Category {
    private Integer id;
    private String categoryName;//分类名称
    private String categoryAlias;//分类别名
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
