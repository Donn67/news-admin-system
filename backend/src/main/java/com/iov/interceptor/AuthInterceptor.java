package com.iov.interceptor;

import com.iov.utils.JwtUtil;
import com.iov.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if ((token.startsWith("Bearer "))){
            token=token.substring(7);
        }
        try{
            Map<String, Object> claims = jwtUtil.parseToken(token);
            Integer id=Integer.valueOf(claims.get("id").toString());
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            String redisToken = opsForValue.get("login:token:" + id);
            if (redisToken==null||!Objects.equals(redisToken,token)){
                throw new RuntimeException();
            }
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
