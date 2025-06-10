package com.project.barointern_13.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
}
