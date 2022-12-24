package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO (Data Transfer Object) : 계층(layer) 사이에서 데이터를 전달할 때 사용하는 객체
// 메서드의 argument 또는 메서드의 리턴 값으로 사용되는 객체
// 이 클래스의 목적은 example/form.html에서 전송하는 폼 데이터들을 저장하기 위한 클래스
// 필드 이름들을 요청 파라미터 이름과 동일하게 선언하면 DispatcherServlet이 객체를 자동으로 생성해준다.
// 변수의 이름들이 form 의 requestParameter의 name과 동일하다.

@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드들을 argument로 갖는 생성자
@Data // @RequiredArgsConstructor + @Getter + @Setter + @ToString + @EqualsAndHashCode
public class FormDto {

    private String userName;
    private String passWord;
    private LocalDateTime due;
    private LocalDate birthday;
    private boolean alarm;
    private List<String> sns;
    
}
