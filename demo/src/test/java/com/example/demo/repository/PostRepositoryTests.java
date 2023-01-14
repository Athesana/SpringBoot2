package com.example.demo.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.PostListResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // OrderAnnotation을 가지고 테스트 메소드들의 순서를 지정하겠다.
@SpringBootTest
public class PostRepositoryTests {
    
    @Autowired // 의존성 주입(dependency injection), IoC(Inversion of Control, 제어의 역전)
    // 객체의 생성을 객체를 사용하는 클래스에서 담당하지 않고, 객체들을 관리하는 스프링 컨텍스트에서 객체 생성과 관리를 담당하고, 필요한 곳에 객체를 자동으로 주입해준다.
    private PostRepository postRepository;
    
    @Test
    @Order(1) // 첫번째로 실행할 테스트 메서드
    public void testSave() {
        Assertions.assertNotNull(postRepository);

        for(int i = 1; i <= 3; i++) {
            // DB 테이블에 insert하기 위한 record를 생성한 것
            Post entity = Post.builder()
                              .title("제목 " + i).content("내용" + i).author("사나 " + i)
                              .build();
            
            log.info("save 호출 전 : {}", entity);
            
            // DB 테이블에 insert 해보
            postRepository.save(entity); // db 테이블과 java 프로그램이 동기화 된다.
            log.info("save 호출 후 : {}", entity); 
        }
    }
    
    @Test
    @Order(2)
    public void testFindAll() {
        List<Post> list =  postRepository.findAll(); // 전체 검색 (select 문)
        Assertions.assertEquals(3, list.size());
        
        for(Post entity : list) {
            log.info("{}", entity);
        }
        
    }
    
    @Test
    @Order(3)
    public void testFindAndSave(){
        /*
         * PK로 검색
         * - findById의 argument로 넘기는 PK 존재 여부에 따라 레코드가 있을 수도, 없을수도 있기 때문에 findById 메서드는 엔터티 타입이 아니라 Optional<엔터티>을 리턴함
         * (1) PK가 존재할 경우 -> RECORD가 존재한다는 뜻 -> get() 메서드는 null이 아닌 Post 타입 객체를 리턴한다.
         * (2) PK가 존재하지 않을 경우 -> RECORD가 존재하지 않는다는 뜻 ->  get() 메서드는 NoSuchElementException 발생
         */
        Post entity = postRepository.findById(1L).get(); // orElseThrow()와 동일
        Assertions.assertNotNull(entity);
        log.info("수정 전 modifiedTime = {}", entity.getModifiedTime());
        
        // DB 테이블에서 검색한 레코드를 수정(update)
        String newTitle = "바꾼 제목";
        String newContent = "바꾼 내용";
        
        entity.update(newTitle, newContent); // 테이블에서 검색한 레코드를 update 한 것, 아직 DB에 저장된건 아님
        
        // 수정된 데이터를 DB에 저장
        /*
         * [save(엔터티) 메서드]
         * 1. 엔터티의 boardNo가 null이면 SQL insert문
         * 2. 엔터티의 boardNo가 null이 아니면 SQL update문
         */
        
        postRepository.save(entity);
        
        Assertions.assertEquals(newTitle, entity.getTitle());
        Assertions.assertEquals(newContent, entity.getContent());
        
        log.info("update 호출 후 : {}", entity); 
        log.info("수정 후 modifiedTime = {}", entity.getModifiedTime());
        
    }
    
    @Test
    @Order(4)
    public void testDelete() {
        // deleteById() 메서드 : select 문 호출 -> delete 문 호출
        postRepository.deleteById(2L);  // RETURN 타입이 없음
        
        List<Post> list =  postRepository.findAll(); // 전체 검색 (select 문)
        
        for(Post entity : list) {
            log.info("{}", entity);
        }
        
        long count = postRepository.count();
        Assertions.assertEquals(2, count);
        log.info("delete 호출 후 record 개수 : {}", postRepository.count()); 
    }
    
    @Test
    @Order(5)
    public void testSearch() {
        //List<Post> list =  postRepository.findByTitleContainsIgnoreCaseOrderByBoardNoDesc("제"); // 전체 검색 (select 문)
        //List<Post> list =  postRepository.findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByBoardNoDesc("3", "3");
        List<PostListResponseDto> list = postRepository.serachBykeyword("3");
        
        for(PostListResponseDto p : list) {
            log.info("search 제목 테스트 " + p.toString() );             
        }
    }
    
    @Test
    @Order(6)
    public void testSearch2() {
        List<Post> list =  postRepository.findByContentContainsIgnoreCaseOrderByBoardNoDesc("내용3") ; // 전체 검색 (select 문)
        int searchCnt = 0;
        for(Post p : list) {
            searchCnt++;
        }
        
        Assertions.assertEquals(1, searchCnt);
        log.info("search 내용 테스트 : {}", searchCnt);     
    }
    
    
    
}
