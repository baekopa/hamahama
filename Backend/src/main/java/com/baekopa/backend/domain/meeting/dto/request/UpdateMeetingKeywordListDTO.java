package com.baekopa.backend.domain.meeting.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateMeetingKeywordListDTO {
    private List<UpdateMeetingKeywordDTO> updateMeetingKeywordList;

    //@Builder
    //private UpdateMeetingKeywordListDTO(List<UpdateMeetingKeywordDTO> updateMeetingKeywordList) {
    //    this.updateMeetingKeywordList = updateMeetingKeywordList;
    //}
    //
    //public static UpdateMeetingKeywordListDTO from (List<UpdateMeetingKeywordDTO> updateMeetingKeywordList){
    //    return builder()
    //            .updateMeetingKeywordList(updateMeetingKeywordList)
    //            .build();
    //}

}
