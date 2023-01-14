package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.PostListResponseDto;

// 인터페이스는 다른 인터페이스를 상속할 수 있다. (interface extends interface) : 상위 인터페이스의 추상 메서드 정의들을 상속받는다.
// JpaRepository<T, ID> 제너릭 인터페이스 -> generic interface
//  - T  : 엔터티 클래스의 타입
//  - ID : 엔터티 클래스에서 @ID 어노테이션이 설정된 필드의 데이터 타입(PK의 데이터 타입)
// JpaRepository 인터페이스는 기본적인 CRUD 작업에 필요한 많은 메서드들이 선언되어있다.
// Spring Data JPA를 사용하면, 스프링 부트 어플리케이션이 컴파일 할 때, 추상 메서드들이 자동으로 구현된다.
public interface PostRepository extends JpaRepository<Post, Long>{

    // 정렬할 때 사용하고 싶은 거는 POST가 가지고 있는 멤버변수 이름으로 넣으면 됨
    // ▼ SELECT * FROM POSTS ORDER BY BOARD_NO DESC;
    List<Post> findByOrderByBoardNoDesc();
    
    // 제목 검색 (SELECT * FROM POSTS WHERE LOWER(TITLE) LIKE LOWER(#TITLE#) ORDER BY BOARD_NO DESC;
    // 메서드를 호출하면서 sql문장을 완성하기 위해서 검색어를 넣어야 해서 인수를 전달한다.
    List<Post> findByTitleContainsIgnoreCaseOrderByBoardNoDesc(String title);
    
    // 내용 검색 (SELECT * FROM POSTS WHERE UPPER(CONTENT) LIKE UPPER(#CONTENT#) ORDER BY BOARD_NO DESC;
    List<Post> findByContentContainsIgnoreCaseOrderByBoardNoDesc(String content);
    
    // 작성자 검색
    List<Post> findByAuthorContainsIgnoreCaseOrderByBoardNoDesc(String author);
    
    // 제목 + 내용 검색
    /*
     * SELECT * FROM POSTS
     * WHERE LOWER(TITLE) LIKE LOWER(#TITLE#) OR LOWER(CONTENT) LIKE LOWER(#CONTENT#) 
     * ORDER BY BOARD_NO DESC;
     */
    List<Post> findByTitleContainsIgnoreCaseOrContentContainsIgnoreCaseOrderByBoardNoDesc(String title, String content);
    
    // JPQL(JPA Query Language)
    // 주의할 것  : FROM에 테이블은 엔티티 클래스의 이름과 같기 때문에 테이블 이름이 아니라 클래스 이름을 쓴다.
    //         : 컬럼 이름을 쓰는것이 아니라 엔터티 클래스 이름과 엔터티 클래스의 변수명을 써야 된다.
    //         : 마지막에 세미콜론(;) 쓰지 않는다.
    //         : 콜론(:)은 변수가 들어가야(argument로 채워줄 부분은) 하는 부분은 :변수 라고 쓴다.
    //         : 같이 사용한다면 같은 변수를 써도 된다.
    @Query(
    
        //"select p.boardNo, p.title, p.content, p.author, p.createdTime, p.modifiedTime " +
        //"select new com.example.demo.repository.Post(p.boardNo, p.title, p.content, p.author)" +
        "select new com.example.demo.dto.PostListResponseDto(p.boardNo, p.title, p.author, p.createdTime) " +
        "from Post p " +
        "where lower(p.title) like lower('%' || :keyword || '%') " + 
        "or lower(p.content) like lower('%' || :keyword || '%') " + 
        "order by p.boardNo desc"
    )
    List<PostListResponseDto> serachBykeyword(@Param("keyword") String keyword);
    
}
