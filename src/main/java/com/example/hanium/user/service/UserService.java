package com.example.hanium.user.service;

import com.example.hanium.config.JwtAuthenticationProvider;
import com.example.hanium.file.domain.FileRepository;
import com.example.hanium.ResponseDto;
import com.example.hanium.user.domain.User;
import com.example.hanium.user.domain.UserRepository;
import com.example.hanium.user.dto.TokenDto;
import com.example.hanium.user.dto.UserDeleteRequestDto;
import com.example.hanium.user.dto.UserLoginRequestDto;
import com.example.hanium.user.dto.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final FileRepository fileRepository;

    @Transactional
    public ResponseDto userRegister(UserRegisterRequestDto userRegisterRequestDto) {

        if(!userRegisterRequestDto.getPassword().equals(userRegisterRequestDto.getCheckPassword())){
            return new ResponseDto("FAIL", "입력한 비밀번호가 서로 다릅니다.");
        }

        if(userRepository.existsByEmail(userRegisterRequestDto.getEmail())){
            return new ResponseDto("FAIL", "이미 존재하는 이메일입니다.");
        }

        User user = userRepository.save(User.builder()
        .email(userRegisterRequestDto.getEmail())
        .name(userRegisterRequestDto.getName())
        .type(userRegisterRequestDto.getType())
        .password(passwordEncoder.encode(userRegisterRequestDto.getPassword()))
        .companyName(userRegisterRequestDto.getCompanyName())
        .registrationNumber(userRegisterRequestDto.getRegistrationNumber())
        .phoneNumber(userRegisterRequestDto.getPhoneNumber())
        .fax(userRegisterRequestDto.getFax())
        .build());

        return new ResponseDto("SUCCESS", user.getUserId());
    }

    public ResponseDto userLogin(UserLoginRequestDto userLoginRequestDto) {

        if (!userRepository.existsByEmail(userLoginRequestDto.getEmail())) {
            return new ResponseDto("FAIL", "존재하지 않는 이메일입니다.");}

        User user = userRepository.findByEmail(userLoginRequestDto.getEmail());
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            return new ResponseDto("FAIL", "비밀번호가 틀렸습니다.");
        }
        // 로그인 할 경우 "AccessToken"과 "RefreshToken"을 "TokenDto"에 넣어 반환
        TokenDto tokenDto = jwtAuthenticationProvider.createToken(user.getEmail(), user.getRoles());

        // 생성된 "RefreshToken"를 "Redis"에 저장, 시간이 만료 되면 자동적으로 삭제
        // key 값: "RT:email",
        redisTemplate.opsForValue().set("RT:"+user.getEmail(),
                tokenDto.getRefreshToken(), tokenDto.getRefreshTokenTime(), TimeUnit.MILLISECONDS);

        return new ResponseDto("SUCCESS", tokenDto);
    }

    public ResponseDto userDelete(UserDeleteRequestDto userDeleteRequestDto) {

        String email = userDeleteRequestDto.getEmail();

        if (!userRepository.existsByEmail(email)) {
            return new ResponseDto("FAIL", "존재하지 않는 이메일입니다.");}

        User user = userRepository.findByEmail(email);
        userRepository.delete(user);

        return new ResponseDto("SUCCESS",user.getUserId());
    }
}