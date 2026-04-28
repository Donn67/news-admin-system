package com.iov.controller;

import com.iov.dto.UserLoginDTO;
import com.iov.dto.UserPwdUpdateDTO;
import com.iov.dto.UserRegisterDTO;
import com.iov.dto.UserUpdateDTO;
import com.iov.pojo.Result;
import com.iov.pojo.User;
import com.iov.service.UserService;
import com.iov.utils.JwtUtil;
import com.iov.utils.SecurityPasswordUtil;

import com.iov.vo.UserVO;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public Result<?> register(@Validated UserRegisterDTO dto) {
        userService.register(dto);
        return Result.success();
    }


    @PostMapping("/login")
    public Result<String> login(@Validated UserLoginDTO dto) {
        String token = userService.login(dto);
        return Result.success(token);
    }


    @GetMapping("/userinfo")
    public Result<UserVO> userInfo() {
        return Result.success(userService.getUserInfoVO());
    }

    // 修改资料（昵称、邮箱）
    @PutMapping("/update")
    public Result update(@RequestBody @Validated UserUpdateDTO dto) {
        userService.updateUser(dto);
        return Result.success();
    }


    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("avatarUrl") @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody @Validated UserPwdUpdateDTO dto) {
        if (!Objects.equals(dto.getNewPwd(), dto.getRePwd())){
            return Result.fail("新しいパスワードと確認用パスワードが一致していません");
        }
        userService.updatePwd(dto);
        return Result.success();
    }
}
