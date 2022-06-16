package com.example.hanium.exception;

import com.example.hanium.user.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiErrorController {

    // 클라이언트 에러 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseDto illegalExHandler(IllegalArgumentException e){
        log.error("[exceptionHandler]", e);
        return new ResponseDto("FAIL","요청 에러");
    }

    // 서버 에러 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseDto exHandler(Exception e){
        log.error("[exceptionHandler]", e);
        return new ResponseDto("FAIL", "서버 내부 에러");
    }

    // 런타임 에러 처리
    @ExceptionHandler
    public ResponseDto runTimeExHandler(RuntimeException e){
        log.error("[exceptionHandler]", e);
        return new ResponseDto("FAIL", "RuntimeException");
    }

    // Null 에러 처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseDto nullExHandler(NullPointerException e){
        log.error("[exceptionHandler]", e);
        return new ResponseDto("FAIL", "NullPointerException");
    }

    // Validation 에러 처리
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto validationExHandler(MethodArgumentNotValidException e){
        log.error("[exceptionHandler]", e);

        return new ResponseDto("FAIL", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        // "e.getBindingResult().getAllErrors().get(0).getDefaultMessage()"를 이용하여 "message" 값 반환
    }

}