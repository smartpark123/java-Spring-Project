package project.first.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter @Setter
public class MemberFormDTO {

    @NotEmpty(message = "이름을 입력해주세요.")
    private String username;

    @NotEmpty(message = "ID를 입력해주세요.")
    private String user_id;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "email을 입력해주세요")
    private String email;

    @NotEmpty(message = "주소를 입력해주세요")
    private String address;

    private int money;
}
