package com.iov.controller;

import com.iov.pojo.Result;
import com.iov.pojo.User;
import com.iov.service.UserService;
import com.iov.utils.SecurityPasswordUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result<?> register(@Pattern (regexp = "^\\S{5,16}$") @RequestParam("username") String username,
                           @Pattern (regexp = "^\\S{5,16}$") @RequestParam("password") String password){
        User u=userService.findByUsername(username);
        if (u==null){
            userService.register(username,password);
            return Result.success();
        }else {
            return Result.fail("ユーザー名が既に存在します");
        }
    }
    @PostMapping("login")
    public Result<String> login(@Pattern (regexp = "^\\S{5,16}$") @RequestParam("username") String username,
                           @Pattern (regexp = "^\\S{5,16}$") @RequestParam("password") String password){
        User loginUser=userService.findByUsername(username);
        if (loginUser==null){
            return Result.fail("ユーザー名またはパスワードが不正");
        }
        if (Objects.equals(SecurityPasswordUtil.getSHA256(password), loginUser.getPassword())){
            return Result.success("jwt占位");
        }
        return Result.fail("ユーザー名またはパスワードが不正");
    }
}
