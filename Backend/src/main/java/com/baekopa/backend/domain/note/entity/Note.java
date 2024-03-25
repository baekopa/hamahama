package com.baekopa.backend.domain.note.entity;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.global.entity.BaseBy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@SQLDelete(sql = "UPDATE note SET deleted_at = NOW() WHERE note_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "summary")
    private String summary;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Note(String title, String content, String summary, Member member) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.member = member;
    }

    public static Note of(String title, String content, String summary, Member member) {
        return Note.builder()
                .title(title)
                .content(content)
                .summary(summary)
                .member(member)
                .build();
    }

    public void updateSummary(String summary) {
        this.summary = summary;
    }

}
