package com.example.hanium.user.dto;

import lombok.Data;

@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenTime;
    private Long refreshTokenTime;
}
