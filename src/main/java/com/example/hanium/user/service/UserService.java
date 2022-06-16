package com.example.hanium.user.service;

import com.example.hanium.config.JwtAuthenticationProvider;
import com.example.hanium.user.ResponseDto;
import com.example.hanium.user.domain.User;
import com.example.hanium.user.domain.UserRepository;
import com.example.hanium.user.dto.TokenDto;
import com.example.hanium.user.dto.UserLoginRequestDto;
import com.example.hanium.user.dto.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final UserDetailsService userDetailsService;

    @Transactional
    public ResponseDto userRegister(UserRegisterRequestDto userRegisterRequestDto) {
        User user = userRepository.save(User.builder()
        .email(userRegisterRequestDto.getEmail())
        .name(userRegisterRequestDto.getName())
        .password(passwordEncoder.encode(userRegisterRequestDto.getPassword()))
        .phoneNumber(userRegisterRequestDto.getPhoneNumber())
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
}