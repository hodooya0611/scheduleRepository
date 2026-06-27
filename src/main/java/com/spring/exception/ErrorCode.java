package com.spring.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ID_NOT_FOUND(HttpStatus.UNAUTHORIZED, "IDまたはパスワードが正しくありません。"),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "IDまたはパスワードが正しくありません。"),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "ユーザーが見つかりません。"),

    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "日程が見つかりません。"),

    CALENDAR_NOT_FOUND(HttpStatus.NOT_FOUND, "カレンダーが見つかりません。"),

    CALENDAR_ACCESS_DENIED(HttpStatus.FORBIDDEN, "アクセス権限がありません。"),

    UPDATE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "修正権限がありません。"),
    ;

    private final HttpStatus status;

    private final String message;


    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
