package com.example.demo.security.controller;

import com.example.demo.security.dto.JwtDTO;
import com.example.demo.security.dto.LoginDTO;
import com.example.demo.security.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/auth")
    public ResponseEntity<JwtDTO> auth(@RequestBody LoginDTO loginDTO) {
        System.out.println("loginDTO = " + loginDTO);
        log.info("loginDTO: {}", loginDTO);

//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                loginDTO.getUsername(), loginDTO.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
//        log.info("authentication: {}", authentication);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtDTO jwtDTO = jwtUtil.generateToken(loginDTO.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtUtil.AUTHORIZATION_HEADER, "Bearer " + jwtDTO.getAccessToken());

        return new ResponseEntity<>(jwtDTO, httpHeaders, HttpStatus.OK);
    }
}