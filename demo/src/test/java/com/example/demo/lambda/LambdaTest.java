package com.example.demo.lambda;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LambdaTest {
    
    // calculator 테스트하기 위한 일종의 메인 메소드 역할을 할 것임
    @Test
    public void test() {
        // Calculator 인터페이스를 구현하는 Adder 타입의 객체를 생성해보자.
        // 다형성 : 상위 타입으로 변수를 선언하고 = 객체 생성은 new 하위타입(); 으로 함
        // adder 는 Calculator 타입 이기도 하면서 Adder 타입이기도 하다.
        // 덧셈 계산기는 계산기이다. -> 맞말
        Calculator adder = new Adder();
        double result= adder.calculate(5, 5);
        log.info("adder 결과 : " + result);
        
        // Calculator 인터페이스를 구현하는 내부 클래스를 선언(정의), 변수랑 똑같이 생각하면 됨
        // 내부 클래스는 지역변수처럼 만들어져 있기 때문에 메소드가 끝나면 자동으로 static에서 빠져서 사라지는 변수 -> 공개 이름 필요 없음
        // (cf. 멤버변수는 객체가 생성되어 있는 동안에는 메모리에 남아 있는 것 -> 어디까지 공개할거야 -> 공개 범위)
        // 다만, 멤버 변수 위치에서 만들었다면 공개 범위를 앞에 붙여야 됨(4개 다 사용가능)
        // 클래스 바디가 있고, 이 클래스 이름을 Subtractor라고 하겠다. 이렇게 하면 객체를 만들 수 있고 
        class Subtractor implements Calculator {
            // override해서 바디를 구현하겠다.
            @Override
            public double calculate(double x, double y) {
                return x - y;
            }
        }
        
        // 내부클래스는 이 클래스로 객체 생성하려고 만드는 거임
        
        Calculator subtractor = new Subtractor();
        double result2 = subtractor.calculate(5, 5);
        log.info("subtractor 결과 : " + result2 );
        
        // 익명 클래스(anonymous class)의 등장!
        // 변수를 선언
        Calculator multiplier = new Calculator() {
            // 클래스에 이름을 주지 않는다. 따라서, 객체를 생성할 방법이 없다. 
            // 역할 : 1) 이름이 없는 클래스를 선언(정의)함과 동시에 2) 객체 생성 (부모 클래스의 이름이 오거나, 인터페이스의 이름이 올 수 있음.) 까지 해버리는 것
            @Override
            public double calculate(double x, double y) {
                return x * y;
            }
        };
        
        double result3 = multiplier.calculate(5, 5);
        log.info("multiplier 결과 : " + result3 );
        
        /*▲ 여기까지 JAVA 7*/
        
        /*▼ 여기부터 JAVA 8 이상 - JAVA에서의 lambda expression*/
        // - 인터페이스가 가지고 있는 추상메서드는 1개여야 한다. (2개 이상이면 못 만듬)
        // - 자바에서의 lambda란 : 추상메서드를 오직 1개만 가지고 있는 인터페이스를 구현한 익명 객체이다.
        // - 함수형 인터페이스(functional interface) : 추상 메서드를 오직 1개만 갖는 인터페이스
        // 파라미터 type 생략 가능, 코드가 여러 줄이 된다면 -> 뒤에 {} 쓰고 그 안에 코드 쓰면 됨
        Calculator divider = (x, y) -> (x * y) + x /y ;
        double result4 = divider.calculate(5, 5);
        log.info("divider 결과 : " + result4 );
        
        
        
        
    }
}
