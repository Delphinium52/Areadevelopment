package com.sparta.areadevelopment.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {

    /**
     * 댓글의 내용입니다.
     */
    private String content;
}
