package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

//스프링 컨텍스트에 컨트롤러 콤포넌트로 등록하겠다. -> DispathcerServlet이 컨트롤러를 사용할 수 있게 된다.
@Controller 
@Slf4j
public class HomeController {
    @GetMapping(value= "/") // GET 방식의, 요청 주소 "/ (contextRoot)"를 처리하는 메서드. 
    public String Home() {
        log.info("home() 호출");
        return "/example/index"; // VIEW(html) 의 이름과 경로
    }
    
}
