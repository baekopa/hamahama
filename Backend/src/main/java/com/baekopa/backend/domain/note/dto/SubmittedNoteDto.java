package com.baekopa.backend.domain.note.dto;

import com.baekopa.backend.domain.note.entity.SubmittedNote;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmittedNoteDto {

    private Long id;
    private String originText;
    private String summaryText;
    private Long writerId;
    private String writerName;
    private String writerImage;

    @Builder
    private SubmittedNoteDto(Long id, String originText, String summaryText, Long writerId, String writerName, String writerImage) {
        this.id = id;
        this.originText = originText;
        this.summaryText = summaryText;
        this.writerId = writerId;
        this.writerName = writerName;
        this.writerImage = writerImage;
    }

//    public static SubmittedNoteDto of(Long id, String originText, String summaryText, Long writerId, String writerName, String writerImage) {
//        return builder().id(id)
//                .originText(originText)
//                .summaryText(summaryText)
//                .writerId(writerId)
//                .writerName(writerName)
//                .writerImage(writerImage)
//                .build();
//    }

    public static SubmittedNoteDto of(SubmittedNote submittedNote) {
        return builder()
                .id(submittedNote.getId())
                .originText(submittedNote.getNote().getContent())
                .summaryText(submittedNote.getNote().getSummary())
                .writerId(submittedNote.getNote().getMember().getId())
                .writerName(submittedNote.getNote().getMember().getName())
                .writerImage(submittedNote.getNote().getMember().getImage()).build();
    }

}
