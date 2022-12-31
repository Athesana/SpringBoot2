package com.example.demo.repository;

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


/*
 * @Entity
 * - DB의 테이블과 자바의 클래스 객체를 매핑하는 객체
 * - 일반적으로 아무런 설정이 없으면 클래스 이름은 Entity 이름과 동일하다. 다르게 하려면 name 속성으로 지정하면 된다.
 * - 즉, name 설정이 없으면 (클래스 이름 = 엔터티 이름 = 테이블 이름) 이다.
 * - name 설정이 있으면, 클래스의 이름과 엔터티 이름을 다르게 하겠다. 그러면 엔터티 이름은 테이블 이름과 같다. (클래스 이름 != 엔터티 이름 = 테이블 이름)
 * - 이 어노테이션이 설정된 클래스의 모든 필드는 테이블의 컬럼으로 정의된다. 
 * (데이터 베이스에 POSTS라는 이름으로 테이블이 만들어지고 그 안에는 boardNo, title, content, author 컬럼들이 생성 + 부모 클래스로부터 물려받은 컬럼도 이 테이블의 컬럼이된다.)
 * (단, 부모클래스에도 테이블과 엔티티가 매핑이 되려면 @MappedSuperclass가 붙어있어야 된다.)
 * - +) 자바 클래스, 필드들은 카멜 표기법(camel-case) 이름을 갖지만, 자동으로 생성되는 컬럼 이름들은 스네이크 표기법(snake-case) 표기법을 따른다. (1:1 매핑이 된다.)
 * 
 * [Entity가 지켜야 할 규칙]
 * - (1) 반드시 기본 생성자가 있어야 한다. (argument가 있는 생성자는 필수 아님)
 * - (2) final class, interface, enum, inner 클래스 불가능. 기본(일반) 클래스여야 한다.
 * - (3) 컬럼이 되는 필드들도 역시 final일 수 없다.
 * - (4) 반드시 primary key 컬럼으로 사용할 필드를 지정해야한다. (***) -> @Id 어노테이션을 사용한다.
 *      -> Entity는 반드시 pk가 필요하다.
 * 
 * @Table
 * - 엔터티 이름과 실제 테이블의 이름을 다르게 설정할 때 사용한다.
 * 
 * 이렇게 하는 이유는 Post는 포스트 1개를 저장하는 클래스이고,
 * 테이블은 그런 포스트를 여러 개 저장하는 곳이니까.
 */

@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드를 argument로 갖는 생성자
@Builder            // all-args 생성자를 이용한 Builder 패턴 코드 자동 생성
@Getter
@ToString(callSuper = true)  // super 클래스의 toStirng을 호출 할 것이냐? (-> 부모타입 엔티티에 가서 toString 만들어줘야됨)
@Entity // JPA에서 사용하는 Entity 이름을 클래스의 이름과 다르게도 설정할 수 있다. (근데 보편적으로는 이름을 똑같이 만든다.)
@Table(name = "POSTS") // 이 설정이 없으면 이 클래스 이름이 테이블의 이름이 됨
public class Post extends BaseTimeEntity{

    // 상속받으면 시간 정보 컬럼을 따로 정의할 필요가 없어진다.
    
    @Id // 테이블의 고유키(PK) 설정
    @GeneratedValue(strategy = GenerationType.AUTO)
    // [PK 생성 규칙(전략)]
    // (1) IDENTITY : MySQL의 auto-increment 기능
    // (2) SEQUENCE : Oracle의 시퀀스 객체
    // (3) TABLE : 키 생성 테이블을을 이용
    // (4) AUTO : 스프링 JPA에서 자동으로 결정한다.
    private Long boardNo; // PK. 포스트 번호.
    
    @Column(nullable = false, length = 512)  // Not Null 제약조건 추가한 것
    private String title;
    @Column(nullable = true, length = 4000)
    private String content;
    @Column(nullable = false)
    private String author;
    // 제약조건이 없는 경우에는 @Column 어노테이션 생략 가능
    
    // setter 메소드를 만들지 않고 값을 변경할 것들만 update를 할 수 있는 메소드
    public Post update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
    
}
