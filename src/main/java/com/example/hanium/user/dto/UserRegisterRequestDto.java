package com.example.hanium.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식을 확인해주세요")
    private String email;

    @NotBlank(message = "패스워드 입력해주세요")
    @Range(min=4,max=12, message = "4~12 글자의 패스워드를 입력해주세요")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;
}
