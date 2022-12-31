package com.example.demo.repository;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

/*
 * [JPA Auditing]
 * - 엔터티의 생성, 수정 이벤트를 처리해서 시간 정보를 자동으로 기록한다.
 * 1. @MappedSuperclass, @EntityListeners 어노테이션을 설정한 클래스를 작성
 * 2. (생성, 수정) 시간 정보가 필요한 엔터티 클래스에서 1번에서 작성한 클래스를 상속한다. (BaseTimeEntity 클래스를 상속하기만 하면 테이블에 컬럼이 생성된다.)
 * 3. JPA Auditing 기능을 Enable(활성화) 해야 한다. (안하면 시간 정보가 기록이 안됨) -> 이 설정은 Main 클래스(DemoApplication.java)에서 @EnableJpaAuditing 어노테이션을 붙이면 된다.
 */

@Getter
@ToString
@MappedSuperclass // 날짜, 시간 정보가 필요한 엔터티 클래스(DB테이블의 컬럼이 될 것이다.)들의 상위 클래스 역할을 한다는 뜻 (테이블에 컬럼들을 만들어주세요)
// -> 상속받는 엔터티 클래스와 매핑되는 데이터 베이스 테이블에 컬럼으로는 여기에서 정의한 컬럼들이 공통적으로 생성된다.
@EntityListeners(AuditingEntityListener.class)
//  엔터티 리스터 설정  : AuditingEntityListener가 엔터티가 생성, 수정, 이벤트가 발생했을 때 이를 감지하고 처리하여 자동으로 시간이 기록되도록 만듦)
public class BaseTimeEntity {
    // 이 클래스는 시간 정보만 가지고 있는 클래스이다.
    
    @CreatedDate // 엔터티가 생성되서 DB에 저장될 때 자동으로 날짜, 시간이 자동으로 기록된다. (생성 이벤트를 감지해라)
    private LocalDateTime createdTime;
    @LastModifiedDate // 조회한 엔터티의 값을 변경(UPDATE)했을 때 날짜, 시간이 자동으로 기록된다. (수정 이벤트를 감지해라)
    private LocalDateTime modifiedTime;
    
}
