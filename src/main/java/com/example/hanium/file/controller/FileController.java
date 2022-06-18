package com.example.hanium.file.controller;

import com.example.hanium.file.service.FileService;
import com.example.hanium.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseDto fileUpload(@RequestPart("file") MultipartFile multipartFile){
        // "@RequestPart("file")": "key"값이 "file"인 튜플을 "multipartFile"로 매핑

        return fileService.fileUpload(multipartFile);
    }
}
