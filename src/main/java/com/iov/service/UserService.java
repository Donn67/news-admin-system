package com.iov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iov.pojo.User;

public interface UserService extends IService<User> {
    User findByUsername(String username);

    void register(String username, String password);
}
