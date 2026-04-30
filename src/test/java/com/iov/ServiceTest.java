package com.iov;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testRedis(){
//        stringRedisTemplate.opsForValue().set("name","Thomas");
//        String name=stringRedisTemplate.opsForValue().get("name");
//        System.out.println(name);
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
//        opsForValue.set("token","yourtoken",12, TimeUnit.SECONDS);
        System.out.println(opsForValue.get("token"));

    }
}
