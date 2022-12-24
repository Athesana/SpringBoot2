package com.example.demo.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.ExampleResponseDto;
import com.example.demo.dto.FormDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/ex1")
    public String ex1() {
        log.info("ex1()");
        return "/example/ex1";
    }
    
    @GetMapping("/ex2")
    public void ex2() {
        log.info("ex2()");
        
        // 컨트롤러 메서드의 리턴값(문자열)은 뷰를 찾기 위한 경로와 이름을 의미한다.
        // 컨트롤러 메서드가 리턴하지 않는 경우, 뷰의 이름은 요청 주소(URI)와 같다.
        // example 폴더 밑에서 ex2.html을 찾는다.
    }
    
    @GetMapping("/ex3")
    public void ex3() {
        log.info("ex3()");
    }
    
    @PostMapping("/ex3")
    public String ex3(String userName, int age, Model model) {
        //컨트롤러 메서드의 파라미터 이름을 요청 파라미터의 이름과 같게 선언하면,
        // DispatcherServlet이 컨트롤 메서드를 호출할 때 요청 파라미터를 분석해서 argument로 전달해준다.
        // 변수로 선언하는 값 = 파라미터, 변수에 넣어주는 값 = argument
        // 요청 파라미터는 무조건 String 
        log.info("ex3(userName={}, age={})", userName, age);
        
        // Model 객체 : 컨트롤러에서 뷰로 데이터를 전달할 때 사용하는 객체
        model.addAttribute("userName", userName);
        model.addAttribute("age", age);
        
        return "/example/result";
    }
    
    @GetMapping("/ex4")
    public String ex4(@RequestParam(name = "userName")String name, @RequestParam(defaultValue = "0") int age, Model model) {
        // @RequestParam 어노테이션 
        // (1) 요청 파라미터의 이름과 컨트롤러 메서드의 파라미터 이름이 다를 때 사용한다.
        // (2) 요청 파라미터 값이 없을 때(빈 문자열일 때("")), 파라미터의 기본값을 설정하기 위해서 사용한다.
        log.info("ex4(name = {}, age = {})", name, age);
        
        model.addAttribute("userName", name);
        model.addAttribute("age", age);
        
        return "/example/result";
    }
    
    @GetMapping("/form")
    public void ex5() {
        log.info("ex5()");
    }
    
    @PostMapping("/form-result")
    public void ex6(FormDto dto) {
        log.info("ex6(dto={})", dto);
        /*
        DispatcherServlet이 요청 파라미터들(userName, passWord, due, ...)을 분석해서
        FormDto 타입의 객체를 생성한다. 
        (객체 생성을 위해서 생성자 호출, setter 메서드 호출하고,
        컨트롤러 메서드를 호출할 때 생성된 객체를 메서드 argument로전달해준다.) 
        */
        
    }
    
    @ResponseBody
    @GetMapping("/rest1")
    public String rest1(String name) {
        // @ResponseBody가 없다면 DispatcherServlet은 html 파일 이름을 찾으려고 할 것이다.
        log.info("rest1의 name은" + name);
        // Controller 메서드에 @ResponseBody 어노테이션이 있으면
        // return하는 문자열이 view를 찾기 위한 이름이 아니라, 클라이언트로 직접 전송되는 응답이였으면 좋겠다.
        return "안녕하세요, " + name + " 님!";
    }
    
    @ResponseBody
    @GetMapping("/rest2")
    public ResponseEntity<ExampleResponseDto> rest2() {
        // 메서드가 호출될 때 전달되는 argument가 없음
        log.info("rest2의 name은");
        
        // 2개의 문자열을 원소로 갖는 sns라는 리스트가 만들어짐
        List<String> sns = Arrays.asList("insta", "youtube");
        ExampleResponseDto responseDto = ExampleResponseDto.builder()
                                            .name("사나")
                                            .age(20)
                                            .brithday(LocalDate.now())
                                            .sns(sns)
                                            .build();
        
        log.info("dto={}", responseDto);
        
        // 이 메서드에서 리턴은 ExampleResponseDto 타입을 엘리먼트로 갖는 ResponseEntity을 리턴하면 됨 
        // ResponseEntity는 서버에서 클라로 응답 보낼 때, 응답 코드가 ok일 때 응답 코드와 응답 내용을 멤버로 갖는 클래스임
        // 응답 responseBody에 들어가는 name은 responseDto임
        
        /*
         컨트롤러 메서드에 @ResponseBody 어노테이션이 있고
         해당 메서드가 자바 객체 타입을 리턴하면,
         DispatcherServlet에서 자바 객체를 JSON 문자열로 변환해서 클라이언트로 응답을 내려준다.
         (단, Dto에서 @Getter 메서드는 반드시 있어야 한다.) 
         */
        
        return ResponseEntity.ok(responseDto);
    }
    
}
