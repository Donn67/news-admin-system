package com.iov.utils;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Objects;
@Component
public class SecurityPasswordUtil {
    private  String SALT = "news-admin-salt";
    private  char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public  String getSHA256(String str) {
        try {
            String saltStr = str + SALT;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(saltStr.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(hexDigits[(b & 0xf0) >> 4]);
                sb.append(hexDigits[b & 0xf]);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public  boolean checkPassword(String raw, String encoded) {
        return Objects.equals(getSHA256(raw), encoded);
    }
}