package com.test.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;

public class JwtUtil {
    // token秘钥
    public static String SECRET = "yourKey";

    /**
     * 解析Jwt字符串
     *
     * @param jwt Jwt字符串
     * @return Claims 解析后的对象
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser().setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(jwt).getBody();
    }

}
