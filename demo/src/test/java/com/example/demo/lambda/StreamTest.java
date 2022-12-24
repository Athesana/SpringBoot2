package com.example.demo.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamTest {
    
    @Test
    public void test() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 10, 21, 32, 54);
        log.info(numbers.toString());
        
        /* [▼ stream 등장 전] */
        //numbers의 원소들 중에서 짝수만 저장한 새로운 리스트 evens 를 만들고 싶어.
        // List는 클래스가 아니라 인터페이스임. 이 인터페이스를 구현하고 있는 ArrayList로 만들겠다.
        // 짝수들을 저장할 빈 리스트 생성
        List<Integer> evens = new ArrayList<>();
        
        // 리스트에 있는 원소들을 처음부터 끝까지 반복하면서 
        // numbers에서 하나씩 꺼내면서 for문 안의 로직을 반복하겠다. 
        for(Integer x : numbers) {
            if(x % 2 == 0) {
                evens.add(x);
            }
        }
        
        System.out.println("짝수 모음 : " + evens);
        
        /* [▼ stream 등장 후] */
        // stream : 데이터가 흘러가는 통로 역할, list를 stream() 객체로 만들어준다. (만들고 난 뒤에는 stream이 가지고 있는 메소드를 활용하는 격)
        // filter 안은 함수 : 값이 들어오면 걸러줌, 조건에 맞는 원소만 다음 메소드로 넘겨준다.
        // filter() 괄호 안에서 적혀있는게 람다식임 => argument가 들어오면 return 값을 ~~ 이렇게 하겠다.
        // toList() (=> collecting) 는 JAVA 11 이상에서 만들어졌음
        List<Integer> odd = numbers.stream().filter(x -> x % 2 != 0).toList();
        System.out.println("홀수 모음 : " + odd);
        
        /* [▼ stream의 MAPPING] */
        // numbers의 원소들 중에서 짝수들의 제곱을 저장하는 리스트를 만들어보자.
        
        // 기존 방식
        List<Integer> evensSquares =new ArrayList<>();
        
        for(Integer x : numbers) {
            // filtering
            if(x % 2 == 0) {
                // mapping
                evensSquares.add(x * x); 
            }
        }
        
        System.out.println("짝수 제곱 모음 : " + evensSquares);
        
        // stream 방식
        // filter : boolean
        // mapping : 리턴 타입에 대한 제약이 없다. 단지 argument가 들어가면 값이 나온다.
        // collecting : toList();
        List<Integer> oddSquares = numbers.stream().filter(x -> x % 2 == 1).map(x -> x * x).toList();

        System.out.println("홀수 제곱 모음 : " + oddSquares);
        
        List<Integer> genders = Arrays.asList(1, 2, 1, 2, 1);
        List<String> genderCodes = new ArrayList<>();
        
        for (Integer x : genders) {
            /*
            if(x == 1) {
                genderCodes.add("남성");
            } else {                
                genderCodes.add("여성");
            }
            */
            genderCodes.add((x == 1) ? "남성" : "여성");
        }
        
        System.out.println(genderCodes);
        
        List<String> genderCodes2 = genders.stream().map(x -> (x == 1) ? "male" : "female").toList();
        
        System.out.println(genderCodes2);
        
    }
    
}
