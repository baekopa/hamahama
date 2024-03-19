package com.baekopa.backend.domain.study.entity;

import lombok.Getter;

@Getter
public enum StudyType {

    GROUP("같이하마"),
    PERSONAL("혼자하마");

    private String typeKor;

    private StudyType(String typeKor) {
        this.typeKor = typeKor;
    }

}
