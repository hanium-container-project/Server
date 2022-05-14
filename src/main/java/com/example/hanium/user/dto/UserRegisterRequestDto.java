package com.example.hanium.user.dto;

import lombok.Data;

@Data
public class UserRegisterRequestDto {

    private String name;
    private String email;
    private String password;
}
