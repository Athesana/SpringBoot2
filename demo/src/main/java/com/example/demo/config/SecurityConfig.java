package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration //스프링 컨텍스트에 Configuration 컴포넌트로 등록하겠다.
// -> 스프링 부트 앱이 실행될 때 클래스에서 설정한 내용들을 로딩하게 된다.(=객체로 가지고 있게 된다.)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // 로그인할 수 있는 가상의 사용자 2명을 만들겠음(JUST LOGIN TEST)
    // DB에 저장되어있지 않고 Memory에 상세 정보 관리자를 만들겠다.
    // 이거 만들고나면은 앱 시작할 때마다 console에 찍히던 테스트용 비밀번호가 자동으로 만들어주지 않는다.
    /*
     * [다형성이 적용된 클래스]
     * UserDetailsService : 상위 (이거 자체는 인터페이스임)
     * InMemoryUserDetailsManager : 하위
     * 
     * - 이제 이렇게 가상의 user가 아니라 DB에 저장된 걸 쓰려면
     * - UserDetailsService를 구현한 클래스가 있어야됨(그 내용은 DB에 있는 것을 SELECT 해오는 내용)
     */
    /*
     * [spring security 필요한 2가지]
     * - TO-BE : 사용자 정보는 아이디, 패스워드, 권한을 가지고 있는 사용자 상세정보라는 타입이여야 하고 아이디 패스워드를 검색하려면 UserDetailsService가 있어야 한다.
     * 1. UserDetails를 구현한 클래스 1개
     * 2. UserDetailsService 를 구현한 클래스 1개 
     * 
     * - 우리가 만든 User 클래스가 UserDetails를 구현하는 내용으로 바꿔주면 됨
     * - userService 클래스에서 UserDetailsService를 구현하도록 바꿔주면 된다.
     */
    /*
    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("1111")).roles("USER").build();
        UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("2222")).roles("USER").build();
        
        return new InMemoryUserDetailsManager(user1, user2);
    }
    */
    
    @Bean // 스프링 부트 앱이 실행될 때 메서드가 리턴하는 객체를 Spring-context에서 bean으로 관리하게 된다.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 브라우저에서 H2-console 사용하기 위해서
        // ajax에서 사용하기 위해서
        httpSecurity.csrf().disable().headers().frameOptions().disable();
        
        // 각 요청 주소에 대한 권한 설정을 Config 클래스에 모두 담당하는 방식
        // 단점 : 서비스가 복잡해질수록 새로운 요청 주소가 매핑될 때마다 수정해야 한다.
        // -> 각 컨트롤러 메서드에서 로그인 필요 여부를 설정하는 방식도 제공 (아래 코드 주석 처리 해야 함)
        /*
        httpSecurity.authorizeHttpRequests() // 요청 URL 권한 부여 설정
                    .requestMatchers("/post/create", "/post/view/**", "/post/modify/**") // 로그인이 필요한 URL
                    .hasRole("USER") // 로그인 시 필요한 권한
                    .anyRequest() // 그 이외의 나머지 모든 요청 주소들
                    .permitAll(); // 로그인 없이(권한 없이) 접근 가능
        */
        
        httpSecurity.csrf().disable()
                    //.httpBasic() // 브라우저에서 제공하는 기본 로그인 다이얼로그를 페이지를 사용할 때
                    .formLogin()   // 스프링 부트에서 제공하는 기본 로그인 페이지를 사용할 때
                    .and()
                    .logout()    // 로그아웃 관련 설정 시작
                    .logoutSuccessUrl("/"); // 로그아웃 후 메인 페이지로 이동
        
        return httpSecurity.build();
    }
    
}
