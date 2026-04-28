package com.iov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iov.dto.UserLoginDTO;
import com.iov.dto.UserPwdUpdateDTO;
import com.iov.dto.UserRegisterDTO;
import com.iov.dto.UserUpdateDTO;
import com.iov.pojo.User;
import com.iov.vo.UserVO;


public interface UserService extends IService<User> {


    User findByUsername(String username);

    void register(UserRegisterDTO dto);

    String login(UserLoginDTO dto);

    UserVO getUserInfoVO();

    void updateUser(UserUpdateDTO dto);

    void updateAvatar(String avatarUrl);

    void updatePwd(UserPwdUpdateDTO dto);
}
