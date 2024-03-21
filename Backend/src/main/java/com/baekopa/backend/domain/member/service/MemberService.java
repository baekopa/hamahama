package com.baekopa.backend.domain.member.service;

import com.baekopa.backend.domain.member.dto.request.MyInfoReqeustDto;
import com.baekopa.backend.domain.member.dto.response.MyInfoResponseDto;
import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.member.repository.MemberRepository;
import com.baekopa.backend.global.response.error.ErrorCode;
import com.baekopa.backend.global.response.error.exception.BusinessException;
import com.baekopa.backend.global.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3UploadService s3UploadService;

    @Transactional(readOnly = true)
    public MyInfoResponseDto getMyInfo(Member currentMember) {
        Member member = memberRepository.findById(currentMember.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_ID_NOT_EXIST, ErrorCode.MEMBER_ID_NOT_EXIST.getMessage()));

        return MyInfoResponseDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .image_url(member.getImage())
                .provider(member.getProvider())
                .build();
    }

    @Transactional
    public Map<String, String> updateMyInfo(Member currentMember, MyInfoReqeustDto myInfoRequestDto) throws IOException {

        Member member = memberRepository.findById(currentMember.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_ID_NOT_EXIST, ErrorCode.MEMBER_ID_NOT_EXIST.getMessage()));

        Map<String, String> result = new HashMap<>();

        if (myInfoRequestDto.getName() != null) {

            member.updateName(myInfoRequestDto.getName());
            result.put("name", myInfoRequestDto.getName());

            return result;
        }

        if (myInfoRequestDto.getImage() != null) {

            MultipartFile image = myInfoRequestDto.getImage();
            String imgUrl = s3UploadService.saveFile("images", image);

            // 이전 이미지 삭제
            if (member.getImage().equals(imgUrl)) {
                s3UploadService.deleteFile(member.getImage());
            }

            member.updateImage(imgUrl);
            result.put("imageUrl", imgUrl);

            return result;
        }

        throw new BusinessException(ErrorCode.NOT_VALID_ERROR, ErrorCode.NOT_VALID_ERROR.getMessage());

    }
}
