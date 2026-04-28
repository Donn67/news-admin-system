package com.iov.pojo;



import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("nd_user")
@Data
public class User {

    private Integer id;
    private String username;
    private String password;

    private String nickname;
    private String email;
    private String userPic;//用户头像地址
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
