package com.project.barointern_13;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barointern_13.dto.request.SignupRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SignupRequestDto validUser;

    @BeforeEach
    void setup() {
        validUser = new SignupRequestDto("testuser", "password123", "테스트유저");
    }

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() throws Exception {
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.nickname").value("테스트유저"))
                .andExpect(jsonPath("$.roles[0].role").value("USER"));
    }

    @Test
    @DisplayName("중복 사용자 회원가입 실패")
    void signupDuplicateFail() throws Exception {
        // 먼저 가입
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));

        // 중복 시도
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.code").value("USER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.error.message").value("이미 가입된 사용자입니다."));
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        // 회원가입 선행
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "username": "testuser",
                              "password": "password123"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void loginWrongPassword() throws Exception {
        // 회원가입 선행
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "username": "testuser",
                              "password": "wrongpassword"
                            }
                        """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("INVALID_CREDENTIALS"));
    }
}
