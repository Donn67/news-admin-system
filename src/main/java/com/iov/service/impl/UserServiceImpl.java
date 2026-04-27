package com.iov.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iov.mapper.UserMapper;
import com.iov.pojo.User;
import com.iov.service.UserService;
import com.iov.utils.SecurityPasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        return this.getOne(queryWrapper);
    }

    @Override
    public void register(String username, String password) {
        String encode = SecurityPasswordUtil.getSHA256(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encode);


        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        save(user);
    }
}
