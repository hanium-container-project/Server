package com.example.hanium.file.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file")
@NoArgsConstructor
@Getter
@Setter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @Column
    private String uploadFileName; // 업로드 된 파일 이름
    @Column
    private String storeFileName; // 업로드 된 파일을 서버에 "저장할 이름"

    @Column(name = "type")
    private String type; // 파일의 유형, ex) 사용자, 회사, 인수예약서 등등.. 추후 재논의

    @Column(name = "userId")
    private Long userId;

    public File(String uploadFileName, String storeFileName){
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
