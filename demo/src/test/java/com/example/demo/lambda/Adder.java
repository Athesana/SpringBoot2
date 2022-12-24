package com.example.demo.lambda;

public class Adder implements Calculator{

    // 상위 타입의 클래스에서 가지고 있는 메서드를 하위 타입의 클래스 상속받아서 구현하는 것
    // 메소드 이름, 리턴 타입, 파라미터 전부 부모 타입과 도일해야한다. 
    // 단, 부모보다 가시성이 넓어지기만 하면 된다. (ex. Filter 와 HttpFilter)
    // 아래처럼 하면 Calculator 인터페이스의 calculate 메소드의 바디를 만들어준 것
    @Override
    public double calculate(double x, double y) {
        return x + y;
    }

}
