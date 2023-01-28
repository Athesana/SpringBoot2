package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // select * from USERS where username = ?
    // DB에 USER가 있으면 USER 객체를, 없으면 NULL을 리턴한다.
    User findByUserName(String username);

}
