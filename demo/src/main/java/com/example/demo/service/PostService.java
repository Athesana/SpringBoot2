package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PostCreateRequestDto;
import com.example.demo.dto.PostListResponseDto;
import com.example.demo.repository.Post;
import com.example.demo.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service // 스프링 컨텍스트에 서비스 컴포넌트로 등록된다.(객체를 생성하고 관리하여 있으며 메모리에 갖고 있다.)
// 서비스 컴포넌트를 필요로 하는 곳에 자동으로 의존성 주입을 할 수 있다.
@RequiredArgsConstructor // final 필드를 초기화하는 생성자를 자동으로 만들어준다.
public class PostService {
    // [의존성 주입; 1번 방식]
    //@Autowired private PostRepository postRepository; // 여기에는 final 붙이면 안됨
    // -> 스프링이 관리하고 있는 객체를 자동으로 넣어주세요.

    // [final field 특성을 이용한 의존성 주입; 2번 방식]
    // final인 멤버변수(필드)는 반드시 "객체 생성 시점"에 초기화가 되어야한다.(값이 들어가야한다.)
    // 직접 값을 넣어주지 않으려면 @RequiredArgsConstructor 생성자가 필요하다.(생성자를 만들어주세요.) => 의존성 주입
    private final PostRepository postRepository;
    
    // ▼ 이런 모습의 생성자를 자동으로 만들어주는 것이 @RequiredArgsConstructor 임
    // 객체 생성할 때 전달된 postRepository의 값으로 멤버 변수에 값을 넣어준다.
    /* 
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    */
    
    // 게시글 등록
    public Long savePost(PostCreateRequestDto postDto) {
        log.info("savePost({}) 호출", postDto);
        
        // DTO -> Entity로 변환해서 repository save하려고
        // service는 직접 repository를 생성하는게 아니여도 호출해서 사용할 수 있다.("의존성 주입")
        // 엔티티를 postRepository(DB)에 save해라.
        Post entity = postRepository.save(postDto.toEntity());
        
        // 저장하면 entity는 boardNo가 생성되었을 것
        return entity.getBoardNo(); // 생성된 포스트의 고유 번호를 리턴한다.
    }

    // 게시글 조회
    public List<PostListResponseDto> read() {
        log.info("read() 호출");
        
        List<Post> list = postRepository.findAll();
        
        //LIST에 있는 원소를 일단 stream() 통로를 통해서 일단 하나 씩 꺼내서 map 메서드한테 넘겨
        //map은 자기가 전달받은 Post 객체 하나하나를 fromEntity 메서드에 아규먼트로 넘긴다.
        //그 다음 list로 만든다.
        //list.stream().map(x -> PostListResponseDto.fromEntity(x)).toList(); 와 같은 코드임
        //람다식에서 아규먼트가 아규먼트로 들어갈 때 생략이 가능하고, 클래스 이름과 메서드 이름만 적어주 돼 :: 를 적는다.
        return list.stream().map(PostListResponseDto::fromEntity).toList();
    }




    
    
    
}
