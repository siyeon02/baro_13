package com.project.barointern_13.dto.response;

import com.project.barointern_13.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Schema(description = "회원가입 응답 DTO")
public class SignupResponseDto {
    @Schema(description = "사용자 이름", example = "JIN HO")
    private String username;
    @Schema(description = "닉네임", example = "Mentos")
    private String nickname;
    @Schema(description = "권한 목록")
    private List<RoleDto> roles;

    @Getter
    @AllArgsConstructor
    @Schema(description = "역할 정보 DTO")
    public static class RoleDto {
        @Schema(description = "역할 이름", example = "USER")
        private String role;

        public static RoleDto from(Role role) {
            return new RoleDto(role.name());
        }
    }

    public static SignupResponseDto fromUser(String username, String nickname, Role role) {
        return new SignupResponseDto(
                username,
                nickname,
                List.of(RoleDto.from(role))
        );
    }
}
