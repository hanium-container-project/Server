package com.example.hanium.user.controller;

import com.example.hanium.user.ResponseDto;
import com.example.hanium.user.dto.UserLoginRequestDto;
import com.example.hanium.user.dto.UserRegisterRequestDto;
import com.example.hanium.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("auth/register")
    @ResponseBody
    public ResponseDto userRegister(@RequestBody UserRegisterRequestDto userRegisterRequestDto){
        return userService.userRegister(userRegisterRequestDto);
    }

    @PostMapping("auth/login")
    @ResponseBody
    public ResponseDto userLogin(@RequestBody UserLoginRequestDto userLoginRequestDto){
        return userService.userLogin(userLoginRequestDto);
    }
}
