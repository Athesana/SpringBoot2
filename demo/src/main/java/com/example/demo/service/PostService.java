package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.PostCreateRequestDto;
import com.example.demo.dto.PostListResponseDto;
import com.example.demo.dto.PostUpdateRequestDto;
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
    @Transactional(readOnly = true) // 서버 부하가 줄어든다. 변경될 일이 없다는 뜻.
    public List<PostListResponseDto> read() {
        log.info("read() 호출");
        
        //List<Post> list = postRepository.findAll();
        List<Post> list = postRepository.findByOrderByBoardNoDesc();
        
        //LIST에 있는 원소를 일단 stream() 통로를 통해서 일단 하나 씩 꺼내서 map 메서드한테 넘겨
        //map은 자기가 전달받은 Post 객체 하나하나를 fromEntity 메서드에 아규먼트로 넘긴다.
        //그 다음 list로 만든다.
        //list.stream().map(x -> PostListResponseDto.fromEntity(x)).toList(); 와 같은 코드임
        //람다식에서 아규먼트가 아규먼트로 들어갈 때 생략이 가능하고, 클래스 이름과 메서드 이름만 적어주면 돼 :: 를 적는다.
        return list.stream().map(PostListResponseDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public Post read(Long no) {
        log.info("read(id={})", no);
        
        // PK로 검색한 데이터 1개, 근데 PK가 존재하지 않으면 값이 없기 때문에 JPA repository 인터페이스에서는 Entity를 원소로 갖는 Optional 타입을 리턴한다.
        // => 데이터가 있을 수도 있고 없을 수도 있는 객체다.
        // Optional에서 Post를 꺼내야 된다.
        return postRepository.findById(no).orElseThrow();
    }
    
    @Transactional // 검색된 엔터티를 수정하면 별도의 save 메서드 호출없이 자동으로 엔터티가 저장된다.(update 문이 실행된다.)
    public Long modifyPost(Long boardNo, PostUpdateRequestDto postDto) {
        log.info("modifyPost(id={}, dto={})", boardNo, postDto);
        
        // id로 먼저 검색을 해
        Post entity = postRepository.findById(boardNo).orElseThrow();
        // 검색된 엔티티를 수정해 (update 하면서 commit 이 일어남)
        // update 메서드는 Post.java 클래스에 정의되어 있음
        entity.update(postDto.getTitle(), postDto.getContent());
        
        // postRepository의 save(entity) 메서드 호출이 필요 없다.
        // 수정하긴 했는데, 값이 변한게 없다면 update는 쓸데 없이 한 번 더 update 문 돌리지 않고 select 한 걸로 그친다.
        
        return entity.getBoardNo();
    }

    public Long deletePost(Long boardNo) {
        log.info("delete(id={})", boardNo);
        
        postRepository.deleteById(boardNo);
        
        return boardNo; // 삭제된 포스트의 고유 번호를 리턴한다.
    }

    public List<PostListResponseDto> search(String type, String keyword) {
        log.info("search(type={}, keyword={})", type, keyword);
        
        // 검색 타입별로 실행할 postRepository의 메서드(SQL 문장)가 다르다.
        // 커스텀 쿼리를 만들어야된다.
        List<PostListResponseDto> list = null;
        switch (type) {
        case "title": {
            list = postRepository.findByTitleContainsIgnoreCaseOrderByBoardNoDesc(keyword)
                    .stream()
                    .map(PostListResponseDto::fromEntity)
                    .toList();
            break;
        }
        case "content": {
            list = postRepository.findByContentContainsIgnoreCaseOrderByBoardNoDesc(keyword)
                    .stream()
                    .map(PostListResponseDto::fromEntity)
                    .toList();
            break;
        }
        case "all": {
            list = postRepository.serachBykeyword(keyword);
            break;
        }
        case "author": {
            list = postRepository.findByAuthorContainsIgnoreCaseOrderByBoardNoDesc(keyword)
                    .stream()
                    .map(PostListResponseDto::fromEntity)
                    .toList();
            break;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + type);
        }
                
        return list;
    }


    
}
