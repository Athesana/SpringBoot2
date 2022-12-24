package com.example.demo.builder;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookTest {

    @Test
    public void testBookGenerate() {
        Book book1 = new Book("하얼빈", "김훈", "문학동네");
        log.info("book1 = {}", book1);
        
        Book book2 = new Book("김훈", "하얼빈", "문학동네");
        log.info("book2 = {}", book2);
        
        /*
         * [Builder(Factory) Pattern 객체 생성]
         * 
         * new 로 Book 객체를 만들지 않았지만, Book 클래스가 가지고 있는 메서드나, 변수를 쓸 수 있다.
         * 즉, 객체 생성하기 전에 클래스 이름으로 호출할 수 있다.
         * builder()는 Book을 리턴하지 않고(즉 Book을 만들지 않고), Book을 만들어줄 수 있는 도구(BookBuilder)를 만든것임
         * ▼ 이 상태는 타입이 안 맞아서 에러가 남
         * Book book3 = Book.builder();
         * 
         * 도구를 만들어서 그 안에서 사용할 수 있는 함수를 쓸 수 있도록 한다.
         * 최종적으로 build()가 드디어 Book을 만들어주는 거임
         * 메서드는 호출 순서가 중요하지 않기 때문에 메서드 호출 순서가 바뀌어도 책의 정보가 오류가 나지 않는다. = Builder Pattern의 장점
         */
        
        Book book3 = Book.builder().author("김훈").title("하얼빈").company("문학동네").build();
        
        log.info("book3 = {}", book3);
    }
}
