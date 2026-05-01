package com.iov.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Map;
@Component
@Data
public class JwtUtil {
    @Value("${jwt.secret}")
    private String KEY ;
	@Value("${jwt.expire}")
    private Long expire;

    public String genToken(Map<String, Object> claims) {

        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .sign(Algorithm.HMAC256(KEY));
    }


    public  Map<String, Object> parseToken(String token) {

        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
