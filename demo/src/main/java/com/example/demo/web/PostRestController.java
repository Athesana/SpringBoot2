package com.example.demo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.PostCreateRequestDto;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostRestController {
    
    // (1) RequiredArgsConstructor + (2) final field ==> 의존성 주입
    private final PostService postService;
    
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PostCreateRequestDto postDto) {
        Long boardNo = 0L;
        log.info("create({}) POST 호출", postDto);
        
        // DB에 저장하겠다.
        boardNo = postService.savePost(postDto);
        
        //return ResponseEntity.ok(0L); // 0을 리턴한 것이 됨
        return ResponseEntity.ok(boardNo);
    }
}
