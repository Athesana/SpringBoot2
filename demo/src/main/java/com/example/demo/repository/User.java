package com.example.demo.repository;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor // 엔터티 클래스는 기본 생성자가 반드시 있어야 한다.
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User extends BaseTimeEntity implements UserDetails {
    // 스프링 시큐리티에서 로그인/로그아웃에서 사용되는 객체는 UserDetails를 구현해야 한다.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO ) // 시퀀스에 의해서 자동으로 생성됨
    private Long id; // 고유키(primary key)
    
    @Column(nullable = false) // NOT NULL 제약 조건
    private String userName; // 로그인 아이디(사용자 이름) -> getUserName();
    
    @Column(nullable = false)
    private String passWord; // 로그인 비밀번호 -> getPassWord();
    
    @Column(nullable = false)
    private String email; // 사용자 이메일

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // GrantedAuthority 타입을 만들면 되고 SimpleGrantedAuthority 생성자를 호출하는데 ROLE_ 을 꼭 붙여야 된다.
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    // 계정이 만료된 것인지 false = 만료 되었다.
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았다.
    }

    // 계정이 잠겼느냐
    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았다.
    }

    // 비밀번호가 만료되었느냐 
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호가 만료되지 않았다.
    }

    // 사용할 수 있는 계정이냐
    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
