package com.example.demo.web;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.PostListResponseDto;
import com.example.demo.repository.Post;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
// 컨트롤러 클래스의 모든 메서드들이 처리하는 요청 주소가 공통의 URL 주소로 시작될 때, 클래스에 @RequestMapping을 설정할 수 있다. 
public class PostController {
    
    // (1) final 필드를 선언 (2) required-args constructor(final 필드를 초기화 하는 생성자) => 의존성 주입이 된다. (컨트롤러가 사용할 postService 객체를 주입받게 된다.)
    private final PostService postService;
    
    @PreAuthorize("hasRole('USER')") // Controller 메서드 실행 전에, 로그인 권한이 있는지를 검사한다.
    @GetMapping("/create") // 클래스에 @RequestMapping 설정이 있기 때문에 실제 이 메서드가 처리하는 요청 주소는 /post/create 이다.
    public void create() {
        log.info("create() 호출");
        /*메서드 리턴타입이 void일 때는 리턴하는 view이름이 없기 때문에 spring은 요청주소랑 동일한 이름인 create.html을 찾는다.*/
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/view/{boardNo}") // 포스트 글번호의 상세 내용 보여주기 기능
    public String view(@PathVariable(name="boardNo") Long no, Model model ) {
        log.info("view(id={})", no);
        
        // 데이터베이스에서 post 정보를 가져와서 view한테 줘야 되니까 model을 선언함        
        // DB에서 포스트 상세 내용을 검색하고, 그 검색한 post 내용을 view에 전달함
        Post post = postService.read(no);
        model.addAttribute("post", post);
        
        return "/post/view";
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/modify/{boardNo}") // 포스트 글번호의 상세 내용 보여주기 기능
    public String modify(@PathVariable Long boardNo, Model model ) {
        log.info("modify(id={})", boardNo);
        
        // 데이터베이스에서 post 정보를 가져와서 view한테 줘야 되니까 model을 선언함        
        // DB에서 포스트 상세 내용을 검색하고, 그 검색한 post 내용을 view에 전달함
        Post entity = postService.read(boardNo);
        model.addAttribute("post", entity);
 
        return "/post/modify";
    }
    
    @GetMapping("/search")
    //@RequestParam 생략가능 : 파라미터 이름 선언할 때, requestParameter와 동일하게만 만들어주면 파라미터의 keyword 값을 넣어준다.
    public String search(String type, String keyword, Model model) {
        
        log.info("search(type={}, keyword={})", type, keyword);

        List<PostListResponseDto> list = postService.search(type, keyword);        
        
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        model.addAttribute("posts", list);
        
        return "home";
    }

}
