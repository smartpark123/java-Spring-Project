package project.first.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class LoginFormDTO {

    @NotEmpty(message = "회원님의 아이디를 입력해주세요")
    private String user_id;

    @NotEmpty(message = "회원님의 비밀번호를 입력해주세요")
    private String user_pw;
}
