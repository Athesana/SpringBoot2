package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/post")
// 컨트롤러 클래스의 모든 메서드들이 처리하는 요청 주소가 공통의 URL 주소로 시작될 때, 클래스에 @RequestMapping을 설정할 수 있다. 
public class PostController {

    @GetMapping("/create") // 클래스에 @RequestMapping 설정이 있기 때문에 실제 이 메서드가 처리하는 요청 주소는 /post/create 이다.
    public void create() {
        log.info("create() 호출");
        /*메서드 리턴타입이 void일 때는 리턴하는 view이름이 없기 때문에 spring은 요청주소랑 동일한 이름인 html을 찾는다.*/
    }
    
    @GetMapping("/view/{boardNo}")
    public String view(@PathVariable Long boardNo) {
        log.info("view() 호출");
        return "";
        
    }
    
}
