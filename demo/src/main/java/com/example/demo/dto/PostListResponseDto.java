package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.repository.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

// 목록 페이지의 테이블에서 보여질 데이터 - 테이블의 행을 나타내는 dto 클래스

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PostListResponseDto {

    private Long boardNo;
    private String title;
    private String author;
    private LocalDateTime createdTime;
    
    // Entity 객체를 DTO 객체로 변환하는 "static" 메서드
    public static PostListResponseDto fromEntity(Post entity) {
        // argument로 post를 전달 받아서 dto를 리턴하는 메서드
        // 이거는 PostService에서 read()메소드를 통해서 받을 수 있음
        return PostListResponseDto.builder()
                .boardNo(entity.getBoardNo())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .createdTime(entity.getCreatedTime())
                .build();
    }
    
}
