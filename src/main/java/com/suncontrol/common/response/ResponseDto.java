package com.suncontrol.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseDto<T> {

    // 요청 성공 여부
    private boolean success;
    private String message;
    private T data;

    // 공통 응답 객체 생성
    public static <T> ResponseDto<T> of(boolean success, String message, T data) {
        return ResponseDto.<T>builder()
                .success(success)
                .message(message)
                .data(data)
                .build();
    }
}