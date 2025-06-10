package com.project.barointern_13.ex;

public class CustomException {
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException() {
            super("이미 가입된 사용자입니다.");
        }
    }

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    public class InvalidTokenException extends RuntimeException {
        public InvalidTokenException() {
            super("유효하지 않은 인증 토큰입니다.");
        }
    }

    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException() {
            super("접근 권한이 없습니다.");
        }
    }
}
