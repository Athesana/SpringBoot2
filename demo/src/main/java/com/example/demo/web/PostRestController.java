package com.example.demo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.PostCreateRequestDto;
import com.example.demo.dto.PostUpdateRequestDto;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostRestController {
    
    /*
     * Ajax(XMLHttpRequest) 요청 방식과 CRUD(Create, Read, Update, Delete)
     * - GET    : Read(SELECT)
     * - POST   : Create(INSERT)
     * - DELETE : Delete(DELETE)
     * - PUT    : Update(UPDATE)
     */
    
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
    
    @PutMapping("/{boardNo}")
    public ResponseEntity<Long> modify(@PathVariable Long boardNo, @RequestBody PostUpdateRequestDto postDto) {
        // 포스트 수정 Ajax 요청 처리 후 수정된 글 번호를 리턴(응답)해보자.
        log.info("modify(id={}, dto={})", boardNo, postDto);

        // DB에 저장하겠다.
        Long result = postService.modifyPost(boardNo, postDto);
        
        //return ResponseEntity.ok(0L); // 0을 리턴한 것이 됨
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{boardNo}")
    public ResponseEntity<Long> delete(@PathVariable Long boardNo) {
        // 포스트 삭제 Ajax 요청 처리 후 삭제한 글 번호를 리턴(응답)해보자.
        log.info("delete(id={})", boardNo);
 
        // DB에서 삭제하겠다.
        Long result = postService.deletePost(boardNo);
        
        //return ResponseEntity.ok(0L); // 0을 리턴한 것이 됨
        return ResponseEntity.ok(result);
    }
}
