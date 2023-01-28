package com.example.demo.dto;

import com.example.demo.repository.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data // getter, setter, toString, ...
public class UserRegisterDto {
    
    // user/register.html 의 form의 input 요소에서 사용된 name 속성 값과 (요청 파라미터 이름과 같게)동일하도록 필드 이름을 작성하면 DispatcherServlet이 자동으로 채워준다.
    private String userName;
    private String passWord;
    private String email;

    // DTO -> Entity 객체로 변환하는 메서드
    public User toEntity() {
        return User.builder().userName(userName).passWord(passWord).email(email).build();
                
    }
    
}
