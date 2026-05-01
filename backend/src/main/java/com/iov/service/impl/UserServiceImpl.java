package com.iov.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iov.dto.UserLoginDTO;
import com.iov.dto.UserPwdUpdateDTO;
import com.iov.dto.UserRegisterDTO;
import com.iov.dto.UserUpdateDTO;
import com.iov.mapper.UserMapper;
import com.iov.pojo.User;
import com.iov.service.UserService;
import com.iov.utils.JwtUtil;
import com.iov.utils.SecurityPasswordUtil;

import com.iov.utils.ThreadLocalUtil;
import com.iov.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SecurityPasswordUtil passwordUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 根据用户名查询
    @Override
    public User findByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return this.getOne(queryWrapper);
    }


    @Override
    public void register(UserRegisterDTO dto) {
        User u = findByUsername(dto.getUsername());
        if (u != null) {
            throw new RuntimeException("ユーザー名が既に存在します");
        }

        String encode = passwordUtil.getSHA256(dto.getPassword());
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encode);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        save(user);
    }

    @Override
    public String login(UserLoginDTO dto) {
        User loginUser = findByUsername(dto.getUsername());

        if (loginUser == null) {
            throw new RuntimeException("ユーザー名またはパスワードが不正");
        }

        if (!Objects.equals(passwordUtil.getSHA256(dto.getPassword()), loginUser.getPassword())) {
            throw new RuntimeException("ユーザー名またはパスワードが不正");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginUser.getId());
        claims.put("username", loginUser.getUsername());
        String token=jwtUtil.genToken(claims);
        Long expire = jwtUtil.getExpire();
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        String key="login:token:"+loginUser.getId();
        opsForValue.set(key,token,expire, TimeUnit.MILLISECONDS);
        return token;
    }


    @Override
    public UserVO getUserInfoVO() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = findByUsername(username);


        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setEmail(user.getEmail());
        vo.setUserPic(user.getUserPic());
        vo.setCreateTime(user.getCreateTime());
        vo.setUpdateTime(user.getUpdateTime());
        return vo;
    }


    @Override
    public void updateUser(UserUpdateDTO dto) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = Integer.valueOf(claims.get("id").toString());

        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getUpdateTime, LocalDateTime.now());
        wrapper.set(User::getEmail, dto.getEmail());
        wrapper.set(User::getNickname, dto.getNickname());
        wrapper.eq(User::getId, id);
        this.update(wrapper);
    }


    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = Integer.valueOf(claims.get("id").toString());

        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getUserPic, avatarUrl);
        wrapper.set(User::getUpdateTime, LocalDateTime.now());
        wrapper.eq(User::getId, id);
        this.update(wrapper);
    }


    @Override
    public void updatePwd(UserPwdUpdateDTO dto) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = Integer.valueOf(claims.get("id").toString());
        User user = getById(id);

        if (!passwordUtil.checkPassword(dto.getOldPwd(),user.getPassword())) {
            throw new RuntimeException("旧パスワードが正しくありません");
        }

        String newPwd = passwordUtil.getSHA256(dto.getNewPwd());
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(User::getPassword, newPwd);
        wrapper.set(User::getUpdateTime, LocalDateTime.now());
        wrapper.eq(User::getId, id);
        this.update(wrapper);
        stringRedisTemplate.delete("login:token:"+id);
    }
}