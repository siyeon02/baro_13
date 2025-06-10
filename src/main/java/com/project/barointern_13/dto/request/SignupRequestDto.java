package com.project.barointern_13.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;;
    private String nickname;

    public SignupRequestDto(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
