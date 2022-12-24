package com.example.demo.web;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ExampleResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
// @Controller : @ResponseBody 어노테이션이 없는 메서드들의 리턴 값은 뷰(html)의 경로와 이름이다.
// @RestController : 클래스의 모든 메서드들의 리턴 값은 뷰의 이름이 아니라 클라이언트로 직접 응답되는 객체이다.(즉, Rest 방식으로만 동작하는 컨트롤러)
// -> RestController의 메서드들에는 @ResponseBody를 사용하지 않는다.
@RequestMapping("/rest")
public class ExampleRestController {
    
    @GetMapping("/rest1")
    public String restApi1(String name) {
        log.info("restApi1(name={})", name);
        return "Hello, " + name + " !"; // 클라이언트로 전달되는 응답.
    }
    
    @GetMapping("/rest2/{name}")
    public String restApi2(@PathVariable String name) {
        // 요청 주소에 포함된 path variable을 argument로 전달받기 위해서는 파라미터 선언 앞에 @PathVariable 어노테이션을 선언하면 된다.
        // 생략하면 요청 파라미터에서 값을 찾으려고 한다.
        // @RequestParam은 생략 가능하지만, @PathVariable은 사용하고자 한다면 생략하면 안된다.
        log.info("restApi2(name={})", name);
        return "안녕하세요, " + name + " !!!!";
    }
    
    @GetMapping("/rest3")
    public ResponseEntity<ExampleResponseDto> restApi3() {
        log.info("restApi3()");
        ExampleResponseDto responseDto = ExampleResponseDto.builder()
                                        .name("홍길동")
                                        .age(20)
                                        .brithday(LocalDate.now())
                                        .build();
        
        log.info("dto={}",  responseDto);
        // 이 리턴값은 DispatcherServlet으로 갔다. (JSON 문자열로 만들어줌)
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<String>> sendList(){
        List<String> list = Arrays.asList("치치", "라라", "다다", "미미", "주주");
        log.info("내새끼들 이름 : " + list);
        return ResponseEntity.ok(list);
    }
}
