package com.baekopa.backend.domain.study.service;

import com.baekopa.backend.domain.study.dto.request.CreateStudyRequestDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final S3UploadService s3UploadService;

    // 새로운 스터디 생성
    public Long createNewStudy(CreateStudyRequestDto requestDto) {

        // 이미지 업로드
        String backgroundImageUrl = uploadImage(requestDto.getBackgroundImage());

        // 새로운 스터디 생성
        Study study = Study.of(requestDto.getTitle(), requestDto.getDescription(), backgroundImageUrl, requestDto.getCategory(),
                requestDto.getStartDate(), requestDto.getEndDate(),
                requestDto.getDay(), requestDto.getStartTime(), requestDto.getEndTime());

        // 스터디장 지정


        // 스터디원 추가


        // DB에 스터디 저장
        study = studyRepository.save(study);
        return study.getId();
    }

    // 스터디 이미지 업로드
    public String uploadImage(MultipartFile image) {

        String imgUrl = "";

        if (image == null || image.isEmpty()) {
            // 배경 선택하지 않았을 때, 기본 이미지 지정
            imgUrl = "https://p22d105s3.s3.ap-northeast-2.amazonaws.com/study_default_img.jpg";
        } else {
            try {
                imgUrl = s3UploadService.saveFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return imgUrl;
    }
}
