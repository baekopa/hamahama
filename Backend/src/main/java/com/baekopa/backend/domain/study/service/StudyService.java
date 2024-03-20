package com.baekopa.backend.domain.study.service;

import com.baekopa.backend.domain.study.dto.request.CreateStudyRequestDto;
import com.baekopa.backend.domain.study.dto.request.UpdateStudyInfoRequestDto;
import com.baekopa.backend.domain.study.dto.response.StudyInfoResponseDto;
import com.baekopa.backend.domain.study.entity.Study;
import com.baekopa.backend.domain.study.repository.StudyRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
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

    private static final String DEFAULT_STUDY_IMAGE = "https://p22d105s3.s3.ap-northeast-2.amazonaws.com/study_default_img.jpg";

    // 새로운 스터디 생성
    public Long createNewStudy(CreateStudyRequestDto requestDto) {

        // 이미지 업로드
        String backgroundImageUrl = uploadImage(requestDto.getBackgroundImage());

        // 새로운 스터디 생성
        Study study = Study.of(requestDto.getTitle(), requestDto.getDescription(), backgroundImageUrl, requestDto.getCategory(), requestDto.getStartDate(), requestDto.getEndDate(), requestDto.getDay(), requestDto.getStartTime(), requestDto.getEndTime());

        // 스터디장 지정


        // 스터디원 추가


        // DB에 스터디 저장
        study = studyRepository.save(study);
        return study.getId();
    }

    public StudyInfoResponseDto getStudyInfo(Long studyId) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, "올바르지 않은 studyId."));

        return StudyInfoResponseDto.from(study);
    }

    // 스터디 기본정보 수정
    public StudyInfoResponseDto updateStudyBasicInfo(Long studyId, UpdateStudyInfoRequestDto requestDto) {

        Study study = studyRepository.findByIdAndDeletedAtIsNull(studyId).orElseThrow(() -> new BusinessException(ErrorCode.STUDY_NOT_EXIST, "올바르지 않은 studyId."));

        // 새 이미지 업로드
        String newImageUrl = uploadImage(requestDto.getBackgroundImage());
        // 현재 이미지 변경 (이전 이미지 s3에서 삭제)
        String backgroundImageUrl = updateImage(study.getBackgroundImage(), newImageUrl);

        // 변경 사항 저장
        study.updateStudyBasicInfo(requestDto.getTitle(), requestDto.getDescription(), backgroundImageUrl, requestDto.getCategory());
        study = studyRepository.save(study);

        return StudyInfoResponseDto.from(study);
    }

    // 스터디 이미지 업로드
    public String uploadImage(MultipartFile image) {

        String imgUrl = "";

        if (image == null || image.isEmpty()) {
            // 배경 선택하지 않았을 때, 기본 이미지 지정
            imgUrl = DEFAULT_STUDY_IMAGE;
        } else {
            try {
                imgUrl = s3UploadService.saveFile(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return imgUrl;
    }

    // 이미지 변경
    public String updateImage(String curImage, String newImage) {

        //  이미지 정보가 변경됐고, 이전 이미지가 기본이미지가 아닐 때
        if (!curImage.equals(newImage) && !curImage.equals(DEFAULT_STUDY_IMAGE)) {
            s3UploadService.deleteFile(curImage);
        }

        return newImage;
    }
}
