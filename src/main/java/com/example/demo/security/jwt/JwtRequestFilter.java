package com.example.demo.security.jwt;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

/*
    클라이언트 요청 시 JWT 인증을 하기 위해 설치하는 커스텀 필터

    1. 클라이언트에서 ID/PW를 통해 로그인을 요청하면
    2. 서버에서 DB에 해당 ID/PW를 가진 User가 있다면, Access Token과 Refresh Token을 발급해준다.
    3. 클라이언트는 발급받은 Access Token을 헤더에 담아서 서버가 허용한 API를 사용할 수 있게 된다.
    Refresh Token: 새로운 Access Token을 발급받기 위한 토큰
    Access Token: 클라이언트가 서버에 요청할 때마다 헤더에 담아서 보내는 토큰
    Access Token은 유효기간이 짧고, Refresh Token은 유효기간이 길다.
    Access Token이 만료되면 Refresh Token을 통해 새로운 Access Token을 발급받을 수 있다.
    Refresh Token이 만료되면 다시 로그인을 해야한다.
    Access Token은 서버에서 발급하고, Refresh Token은 클라이언트에서 발급한다.
    Access Token은 서버에서 발급하기 때문에 서버에서 유효성 검사를 할 수 있다.
    Refresh Token은 클라이언트에서 발급하기 때문에 서버에서 유효성 검사를 할 수 없다.
    Access Token은 유효기간이 짧기 때문에 탈취당해도 큰 문제가 없다.
    Refresh Token은 유효기간이 길기 때문에 탈취당하면 큰 문제가 발생한다.
 */

@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
