package com.project.barointern_13.controller;

import com.project.barointern_13.dto.request.LoginRequestDto;
import com.project.barointern_13.dto.request.SignupRequestDto;
import com.project.barointern_13.dto.response.LoginResponseDto;
import com.project.barointern_13.dto.response.SignupResponseDto;
import com.project.barointern_13.entity.Role;
import com.project.barointern_13.entity.User;
import com.project.barointern_13.ex.CustomException;
import com.project.barointern_13.security.JwtUtil;
import com.project.barointern_13.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "AuthController API", description = "회원가입, 로그인, 관리자 권한 부여 API")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "회원가입",
            description = "신규 사용자 정보를 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "409", description = "이미 가입된 사용자")
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        User user = authService.signup(requestDto);
        SignupResponseDto responseDto = SignupResponseDto.fromUser(user.getUsername(), user.getNickname(), user.getRoles());
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "로그인",
            description = "로그인 후 JWT 토큰을 발급받습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 올바르지 않음")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        String token = authService.login(requestDto);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Operation(
            summary = "관리자 권한 부여",
            description = "관리자 권한을 가진 사용자가 다른 사용자에게 ADMIN 권한을 부여합니다.",
            parameters = {
                    @Parameter(name = "Authorization", description = "Bearer 토큰", in = ParameterIn.HEADER, required = true),
                    @Parameter(name = "userId", description = "권한을 부여할 사용자 ID", in = ParameterIn.PATH, required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "관리자 권한 부여 성공"),
                    @ApiResponse(responseCode = "403", description = "접근 권한 없음"),
                    @ApiResponse(responseCode = "404", description = "사용자 없음")
            }
    )
    @PatchMapping("/admin/users/{userId}/roles")
    public ResponseEntity<SignupResponseDto> grantAdminRole(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String authHeader
    ) {
        // 토큰에서 현재 사용자 권한 검사
        String token = authHeader.replace("Bearer ", "");
        Role currentUserRole = jwtUtil.extractRole(token);

        if (currentUserRole != Role.ADMIN) {
            throw new CustomException.AccessDeniedException(); // 403
        }

        User updatedUser = authService.grantAdmin(userId);
        SignupResponseDto responseDto = SignupResponseDto.fromUser(updatedUser.getUsername(), updatedUser.getNickname(), updatedUser.getRoles());
        return ResponseEntity.ok(responseDto);
    }
}
