package com.example.demo.security.jwt;

import com.example.demo.security.dto.JwtDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void 토큰_발급_테스트(){
        JwtDTO dto = jwtUtil.generateToken("mwcha");
        System.out.println(dto);
    }

    @Test
    public void 아이디가져오기(){
        JwtDTO dto = jwtUtil.generateToken("mwcha");
        String username = jwtUtil.extractUsername(dto.getAccessToken());
        Assertions.assertEquals("mwcha", username);
    }

    @Test
    public void 정보가져오기(){
        JwtDTO dto = jwtUtil.generateToken("mwcha");
        jwtUtil.getAuthentication(dto.getAccessToken());

    }
}