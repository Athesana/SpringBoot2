package com.example.demo.lambda;

public interface Calculator {

    /*
     * JAVA에서의 InterFace는 특별한 클래스이다.
     * - 메소드를 호출(사용)하는 쪽과, 메소드를 개발하는 쪽과의 스펙을 정의하고 약속하기 위한 용도
     * - 추상 메서드들을 구현하기 위한 설계도 용도
     * - 직접 메소드를 구현하지 않기 때문에 메소드에 body가 없다.(추상 메서드) 
     * 만약 일반 클래스에서 쓴다면,
     * abstract class A{
     *      abstract void test();
     * }
     * 처럼 class와 method에 abstract가 붙어 있어야 한다.
     * 
     * - 따라서 인터페이스는 객체를 생성할 수 없다.
     * - 필드(멤버 변수)를 선언할 때 맘대로 선언할 수 없다.
     * 
     * [Java 7 버전까지]
     * 1) 인터페이스가 갖는 반드시 모든 필드(멤버 변수)는 public, static(객체 생성없이 변수가 만들어져야 하고), final(한번 값이 들어가면 바뀌지 않고 값을 변경할 수 없다)이여야 한다. 상수들만 정의한다.
     * 2) 인터페이스가 갖는 반드시 모든 멤버 메서드는 public abstract 이다.
     * 
     * [Java 8 버전에서 추가]
     * 3) default 메서드 : public이고 body가 구현된 인터페이스의 메서드 -> public은 생략이 가능함
     * default void testDefault(){
     *     // to-do
     *     // 얘는 바디가 있는 메서드임
     * }
     * - "인터페이스에서도 body가 있었으면 좋겠다~"
     * - 누군가가 이 인터페이스를 구현하는 클래스를 만들어주고, 바디가 생겼으니까 생성자를 호출해서 객체가 만들어지고 나서야 default를 호출할 수 있음. 객체 생성 전에는 호출할 수 없는 메서드가 됨.
     * 
     * - "객체 생성 전에도 쓸 수 있는 메서드가 있었으면 좋겠다~"
     * - 그래서 이게 생김
     * 4) (public) static 메서드 : public이며 static이고 body가 구현된 인터페이스의 메서드 (public 생략 가능, static 생략 불가능)
     * static void testStatic(){
     *      // to-do 
     * }
     * - 객체 생성 없이도 호출할 수 있는 메서드가 됨.
     * - static은 static만 호출할 수 있다.
     * - new 전에도 class 이름만 있으면 호출할 수 있음.
     * 
     * [Java 9 버전에서 추가]
     * - plus! 아래 것도 생김
     * - "body가 너무 길어서 쪼갠 메서드인데 외부에 공개하기는 싫다~"
     * 5) private static 메서드 : private, static 둘 다 생략 불가능
     * private static void testPrivateStatic(){
     *      // to-do
     * } 
     * - 외부에 공개하지 않고 나만 쓸거고, 객체 생성하지 않고도 쓸 수 있고, body도 가지고 있는 메서드임
     */
    
    //public static final double PI = 3.14;
    double PI = 3.14; // public static final은 생략 가능
    
    //double을 리턴하는 메소드를 만들어보자.
    double calculate(double x, double y); // public abstract 가 생략되어 있는 것, 이 부분을 구현할 클래스에서 얘가 보여야 되기 때문에 public이여야 됨
    
    default void testDefault(){
    }
    
    static void testStatic(){
    }
    
    private static void testPrivateStatic(){
    }
}


// Java visibility : private < (package) : 접근제어자가 생략되어있거나, 같은 패키지 내에서만 사용 < protected : 같은 패키지에 있거나, 상속받아서 사용하는 경우 < public