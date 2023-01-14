package com.example.demo.dto;

import com.example.demo.repository.Post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter, setter, toString, equals, hashCode
@NoArgsConstructor 
public class PostUpdateRequestDto {

    private String title;
    private String content;
    
    public Post toEntity() {
        return Post.builder().title(title).content(content).build();
    }
}
