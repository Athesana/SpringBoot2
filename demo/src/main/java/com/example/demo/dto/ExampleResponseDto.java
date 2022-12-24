package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor // 모든 필드들을 argument로 갖는 생성자
@Builder // Builder pattern(Factory pattern) 패턴 내부 클래스와 메서드
@ToString// toString 메서드를 override 해줌
@Getter // getter 메서드
public class ExampleResponseDto {
    private String name;
    private int age;
    private LocalDate brithday;
    private List<String> sns;
}
