package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

// 인터페이스는 다른 인터페이스를 상속할 수 있다. (interface extends interface) : 상위 인터페이스의 추상 메서드 정의들을 상속받는다.
// JpaRepository<T, ID> 제너릭 인터페이스 -> generic interface
//  - T  : 엔터티 클래스의 타입
//  - ID : 엔터티 클래스에서 @ID 어노테이션이 설정된 필드의 데이터 타입(PK의 데이터 타입)
// JpaRepository 인터페이스는 기본적인 CRUD 작업에 필요한 많은 메서드들이 선언되어있다.
// Spring Data JPA를 사용하면, 스프링 부트 어플리케이션이 컴파일 할 때, 추상 메서드들이 자동으로 구현된다.
public interface PostRepository extends JpaRepository<Post, Long>{

    
}
