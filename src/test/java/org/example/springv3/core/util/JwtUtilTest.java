package org.example.springv3.core.util;

import org.example.springv3.user.User;
import org.junit.jupiter.api.Test;

public class JwtUtilTest {

    @Test
    public void create_test(){
        User user = User.builder().id(1).username("ssar").build();

        String accessToken = JwtUtil.create(user);
        System.out.println(accessToken);
    }

    @Test
    public void verify_test(){
        String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLrsJTrs7QiLCJpZCI6MSwiZXhwIjoxNzI3NDAyODAzLCJ1c2VybmFtZSI6InNzYXIifQ.3H4UzuAjexycdCAQo3n3tN6Imn3cTGAxSMZgpiEHBuUK0cWqxEMZg1h3V1V5_w7OAO7Dn2vcbeF2ky-68aDR4g";
        User user = JwtUtil.verify(accessToken);
        System.out.println(user.getId());
        System.out.println(user.getUsername());
    }
}