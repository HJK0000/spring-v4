package org.example.springv3.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.springv3.user.User;

import java.util.Date;

public class JwtUtil {

    public static String create(User user){
        String accessToken = JWT.create()
                .withSubject("바보") // sub는 아무거나 적어도 됨
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 토큰의 만료시간 - 일주일
                .withClaim("id", user.getId()) // payload
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512("metacoding")); // 우리가 만들고 검증할 거니까 RSA 쓰지 말고 대칭키로 하기

        return accessToken;
    }
    
    // 검증 코드
    // JWT에 서 ID를 꺼내서
    // USER 객체를 만들어서 RETURN
    public static User verify(String jwt){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("metacoding")).build().verify(jwt);
        int id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();

        return User.builder()
                .id(id)
                .username(username)
                .build();
    }
    

}
