package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.PostListResponseDto;
import com.example.demo.repository.Post;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller //스프링 컨텍스트에 컨트롤러 콤포넌트로 등록하겠다. -> dispatcherServlet이 컨트롤러를 사용할 수 있게 된다.
@RequiredArgsConstructor
public class HomeController {
    
    //Controller는 Service 컴포넌트의 메서드를 이용한다.
    //HomeController 객체가 생성될 떄 null이 아닌 값이 들어간다.
    private final PostService postService;
    
    @GetMapping(value= "/") // GET 방식의, 요청 주소 "/ (contextRoot)"를 처리하는 메서드. 
    public String Home(Model model) {
        // Model 객체 : 컨트롤러에서 뷰에 전달한 데이터를 저장하기 위한 객체
        log.info("home() 호출");
        
        List<PostListResponseDto> list = postService.read();
        
        model.addAttribute("posts", list);
        
        //return "/example/index"; // VIEW(html) 의 이름과 경로
        return "home"; // templates/home.html
    }

    
}
