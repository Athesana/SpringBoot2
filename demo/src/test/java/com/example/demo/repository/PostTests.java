package com.example.demo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest // Spring Context(환경설정정보)를 사용한 단위 테스트를 하기 위한 어노테이션
// - application.properties 파일에서 설정한 정보를 이용할 수 있다.
// - @Controller, @Service, ... 등 스프링 어노테이션으로 설정된 객체들을 이용할 수 있다.
public class PostTests {
    
    @Test
    public void postCreateTest() {
        Post post = new Post(); // 기본 생성자를 사용한 객체 생성
        //Assertions.assertNull(post); // failed
        Assertions.assertNotNull(post); // success
        
        log.info("post={}", post ); // post=Post(boardNo=null, title=null, content=null, author=null)
                                    // post=Post(super=BaseTimeEntity(createdTime=null, modifiedTime=null), boardNo=null, title=null, content=null, author=null)
        
        // Builder 패턴을 사용한 객체 생성(필드 이름들이 메서드가 됨)
        Post post2 = Post.builder()
                         .title("테스트 제목")
                         .content("테스트 내용")
                         .author("이산아")
                         .build();
        
        Assertions.assertNotNull(post2);
        Assertions.assertEquals("테스트 제목", post2.getTitle());
        // assertEquals(성공일 때의 정상값(기대값), 실젯값)
        
        log.info("post2={}", post2); // post2=Post(super=BaseTimeEntity(createdTime=null, modifiedTime=null), boardNo=null, title=테스트 제목, content=테스트 내용, author=이산아)
    }
    
}
