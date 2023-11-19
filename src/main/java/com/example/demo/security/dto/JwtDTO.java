package com.example.demo.security.dto;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtDTO {

    // 설명
    // JwtDTO는 JWT를 생성할 때 필요한 정보를 담고 있는 DTO입니다.
    // JWT를 생성할 때 필요한 정보는 다음과 같습니다.
    // 1. grantType: JWT를 생성할 때 사용할 grant type입니다.
    // grantType이란 인증 방식을 의미합니다. 여기서는 password를 사용합니다. (참고: https://developer.okta.com/docs/guides/implement-password/overview/)
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
