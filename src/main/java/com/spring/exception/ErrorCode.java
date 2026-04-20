package com.spring.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "IDまたはパスワードが正しくありません。"),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "IDまたはパスワードが正しくありません。"),
    ;

    private final HttpStatus status;

    private final String message;


    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
