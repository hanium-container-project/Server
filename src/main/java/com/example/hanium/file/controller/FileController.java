package com.example.hanium.file.controller;

import com.example.hanium.file.service.FileService;
import com.example.hanium.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload/{id}")
    public ResponseDto fileUpload(@RequestPart("file") MultipartFile multipartFile, @RequestParam("type") String type,
                                  @RequestParam("userId") Long userId){
        // "@RequestPart("file")": "key"값이 "file"인 튜플을 "multipartFile"로 매핑

        return fileService.fileUpload(multipartFile, type, userId);
    }
}
