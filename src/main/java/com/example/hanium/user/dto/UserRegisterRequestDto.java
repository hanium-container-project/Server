package com.example.hanium.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Email(message = "이메일 형식을 확인해주세요")
    private String email;

    @Size(min=4,max=12, message = "4~12 글자의 패스워드를 입력해주세요")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;
}
