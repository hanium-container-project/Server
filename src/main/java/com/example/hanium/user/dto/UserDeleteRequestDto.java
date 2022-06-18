package com.example.hanium.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserDeleteRequestDto {

    @Email(message = "이메일 형식을 확인해주세요")
    private String email;

}
