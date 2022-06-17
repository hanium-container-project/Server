package com.example.hanium.file.service;

import com.example.hanium.file.domain.File;
import com.example.hanium.file.domain.FileRepository;
import com.example.hanium.user.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("${spring.servlet.multipart.location}") // 해당 경로 위치는 "application.properties"에 존재, ${} 유의
    private String fileDirPath;
    private final FileRepository fileRepository;

    public ResponseDto fileUpload(MultipartFile multipartFile) {
        log.info("multiPartFile = {}", multipartFile);
        log.info("multiPartFileName = {}", multipartFile.getOriginalFilename());

        try{
            File file = storeFile(multipartFile);
            // 첫번째가 사용자가 지정한 파일 이름, 두번째가 서버에 저장할 파일 이름
            log.info("file = {}",file);

            if(null == file){
                return new ResponseDto("FAIL", "파일 업로드 오류");
            }
            else{
                fileRepository.save(file);
            }
        }
        catch (IOException e){
            return new ResponseDto("FAIL", "파일 저장 오류");
        }

        return new ResponseDto("SUCCESS","파일 저장 성공");

    }

    public String getFullPath(String filename){
        return fileDirPath + filename;
    }

    public File storeFile(MultipartFile multipartFile) throws IOException { // "MultiPartFile"을 "storeFile"로 반환해주는 메소드
        List<String> forReturn = new ArrayList<>();

        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        // 사용자가 업로드(입력)한 파일 이름, ex) image.png, 상권이미지.png

        String ext = extractExt(originalFileName);
        // "ext"는 확장자, ex) .png, .jpg ...

        String uuid = UUID.randomUUID().toString();

        // 서버에 저장한 파일 이름
        String storeFileName = uuid + "." + ext;

        // "fileDirPath + storeFileName"의 파일 객체가 생성 후 저장
        multipartFile.transferTo(new java.io.File(getFullPath(storeFileName)));
        return new File(originalFileName, storeFileName);
    }

    private String extractExt(String originalFileName){
        int pos = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(pos + 1);

        return ext;
    }
}
