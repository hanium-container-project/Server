package com.example.hanium.user.controller;

import com.example.hanium.user.ResponseDto;
import com.example.hanium.user.dto.UserLoginRequestDto;
import com.example.hanium.user.dto.UserRegisterRequestDto;
import com.example.hanium.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user") // "URL Mapping"들이 "auth"로 시작
public class UserController {

    private final UserService userService;

    @PostMapping("/register") // URL 경로: "user/register"
    public ResponseDto userRegister(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        return userService.userRegister(userRegisterRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto userLogin(@RequestBody UserLoginRequestDto userLoginRequestDto){
        return userService.userLogin(userLoginRequestDto);
    }
}
