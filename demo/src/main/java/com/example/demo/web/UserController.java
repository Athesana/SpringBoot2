package com.example.demo.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.UserRegisterDto;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor // 컨트롤러 객체가 생성될 때, final 변수들에는 null이 아닌 값이 채워진다. passwordEncoder 타입의 bean이 있어야 한다. (in SecurityConfig에서 @Bean으로 등록되어 메모리상에 있을 것이다.)
public class UserController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // SecurityConfig에서 @Bean으로 선언된 PasswordEncoder객체가 주입됨. DB에 저장하기 전에 PW 암호화 하려고 사용
    
    @GetMapping("/register")
    public void register() {
        log.info("register() GET");
    }
    
    
    @PostMapping("/register")
    public String register(UserRegisterDto userRegiDto) {
        // 회원가입에서 넘어오는 정보(비밀번호가 날 것 그대로 넘어와서 암호화를 해야 한다.)
        log.info("register({}) POST", userRegiDto);
        
        userRegiDto.setPassWord(passwordEncoder.encode(userRegiDto.getPassWord())); // 비밀번호 암호화
        Long id = userService.create(userRegiDto); // DB에 저장
        log.info("아이디={} ", id);
        
        return "redirect:/login"; // 회원 가입 성공 후 로그인 페이지로 이동
    }
    
}
