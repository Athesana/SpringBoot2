package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserRegisterDto;
import com.example.demo.repository.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    // 스프링 시큐리티에서 로그인/로그아웃에서 사용할 UserDetails 정보를 검색할 수 있는 메서드를 제공
    
    private final UserRepository userRepository;
    
    public Long create(UserRegisterDto userRegiDto) {
        log.info("create({})", userRegiDto);
        
        User user = userRepository.save(userRegiDto.toEntity()); // 엔터티를 실제 DB에 save하겠다는 뜻. 
        
        return user.getId(); // DB에 회원 정보를 INSERT할 때 생성된 시퀀스 값(PK)
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름을 줄 테니까, 사용자 상세 정보를 가지고 있는 객체를 만들어주면 로그인/로그아웃 처리는 해줄게 by 스프링
        // 여기에서는 DB에서 검색한 사용자 정보를 리턴하면 된다.
        // 만약에 사용자 정보가 없으면 throws UsernameNotFoundException 된다. (null을 리턴하지 않도록 주의)
        User userEntity = userRepository.findByUserName(username);
        if(userEntity == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다. " + username);
        }
        return userEntity;
    }

}
