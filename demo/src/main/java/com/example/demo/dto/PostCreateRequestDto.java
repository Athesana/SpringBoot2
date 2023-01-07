package com.example.demo.dto;

import com.example.demo.repository.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCreateRequestDto {
    
    // 요청 파라미터와 이름이 같도록 필드 이름을 선언하면, DispatcherServlet에 의해서 auto-binding 됨(값들이 자동으로 설정된다.)
    private String title;
    private String author;
    private String content;
    
    // DTO 객체를 Entity 객체로 변환해주는 메서드.
    public Post toEntity() {
        return Post.builder().title(title).author(author).content(content).build();
    }
}
