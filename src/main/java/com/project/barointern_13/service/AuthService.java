package com.project.barointern_13.service;


import com.project.barointern_13.dto.request.LoginRequestDto;
import com.project.barointern_13.dto.request.SignupRequestDto;
import com.project.barointern_13.entity.User;

public interface AuthService {
    User signup(SignupRequestDto requestDto);
    String login(LoginRequestDto requestDto);
    User grantAdmin(Long userId);
}

