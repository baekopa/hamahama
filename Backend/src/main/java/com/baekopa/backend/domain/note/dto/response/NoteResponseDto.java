package com.baekopa.backend.domain.note.dto.response;

import com.baekopa.backend.domain.meeting.dto.response.SharedMeetingDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NoteResponseDto {

    private Long id;
    private String title;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
    private String writerName;
    private String writerImage;
    private String summary;
    private List<SharedMeetingDto> meetings;
    @Builder
    private NoteResponseDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String writerName, String writerImage, String summary, List<SharedMeetingDto> meetings) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerName = writerName;
        this.writerImage = writerImage;
        this.summary = summary;
        this.meetings = meetings;
    }

    public static NoteResponseDto of(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String writerName, String writerImage, String summary, List<SharedMeetingDto> meetings) {
        return builder().id(id)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .writerName(writerName)
                .writerImage(writerImage)
                .summary(summary)
                .meetings(meetings)
                .build();
    }

}
