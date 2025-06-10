package com.project.barointern_13.service;


import com.project.barointern_13.dto.request.LoginRequestDto;
import com.project.barointern_13.dto.request.SignupRequestDto;
import com.project.barointern_13.entity.Role;
import com.project.barointern_13.entity.User;

import com.project.barointern_13.ex.CustomException;
import com.project.barointern_13.repository.UserRepository;
import com.project.barointern_13.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User signup(SignupRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new CustomException.UserAlreadyExistsException();
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .roles(Role.USER)
                .build();

        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(CustomException.InvalidCredentialsException::new);

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException.InvalidCredentialsException();
        }

        return jwtUtil.createToken(user.getUsername(), user.getRoles());
    }

    @Override
    public User grantAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setRoles(Role.ADMIN);
        return userRepository.save(user);
    }
}
