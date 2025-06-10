package com.project.barointern_13.ex;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExists(CustomException.UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponseDto.of("USER_ALREADY_EXISTS", ex.getMessage()));
    }

    @ExceptionHandler(CustomException.InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentials(CustomException.InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDto.of("INVALID_CREDENTIALS", ex.getMessage()));
    }

    @ExceptionHandler(CustomException.InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidToken(CustomException.InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseDto.of("INVALID_TOKEN", ex.getMessage()));
    }

    @ExceptionHandler(CustomException.AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDenied(CustomException.AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseDto.of("ACCESS_DENIED", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDto.of("INTERNAL_SERVER_ERROR", "알 수 없는 오류가 발생했습니다."));
    }


}