package com.project.barointern_13.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Role roles;
}
