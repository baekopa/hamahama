package com.baekopa.backend.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RemindQuizDTO {

    @JsonFormat(pattern = "yyy-MM-dd HH:mm")
    private LocalDateTime openDate;
    private String quiz;

    @Builder
    private RemindQuizDTO(LocalDateTime openDate, String quiz) {
        this.openDate = openDate;
        this.quiz = quiz;
    }

    public static RemindQuizDTO of(String quiz){
        return builder()
                .openDate(LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(7))
                .quiz(quiz)
                .build();
    }
}
