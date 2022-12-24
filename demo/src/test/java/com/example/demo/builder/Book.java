package com.example.demo.builder;

// 롬복 : @builder 어노테이션(구현 과정)
// 도서 정보를 가지고 있는 '책' 이라는 클래스를 만들어보자.
public class Book {
    private String title;
    private String author;
    private String company;
    
    // 일반적인 생성자의 모습
    // 문제점 : 정해진 순서대로 호출해야 된다. argument 순서가 바뀐다면 book의 정보가 바뀔 수 있음 
    public Book(String title, String author, String company) {
        this.title = title;
        this.author = author;
        this.company = company;
    }

    // 롬복 : @toString 어노테이션
    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", company=" + company + "]";
    }
    
    // builderPattern 이용해보기
    // 내부클래스(static inner class)
    public static class BookBuilder{
        // to-do : 외부 클래스(outer class)가 가지고 있는 외부 클래스의 필드들을 그대로 선언
        private String title;
        private String author;
        private String company;
        
        // like Setter : 여기에서 this는 바로 위의 필드를 가리킴
        // 생성된 객체에서 title 메서드를 호출할 건데 String title을 argument로 전달해줄 거고, 내가 가지고 있는 title에 저장할 것이다.
        // 다시 this(나의 주소) 메서드를 호출한 객체의 주소를 리턴하겠다. 즉, 나를 return 하겠다.
        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }
        
        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }
        
        public BookBuilder company(String company) {
            this.company = company;
            return this;
        }
        
        // 외부 클래스(outer class) 타입의 객체를 생성해서 리턴하는 메서드
        public Book build() {
            return new Book(title, author, company);
        }
        
    }
    
    // public static method 만들기 : Book 객체를 생성하지 않고, 클래스 이름으로 호출할 수 있는 메서드(객체 생성하지 않으니까 변수가 없어! 그래서 ~. 이 안되서 class 이름을 붙인다.)
    // class타입은 return 타입이 될 수 있다.
    public static BookBuilder builder() {
        return new BookBuilder();
    }
    
}
