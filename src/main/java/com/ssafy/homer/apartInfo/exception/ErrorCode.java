package com.ssafy.homer.apartInfo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    APART_NOT_FOUND(400,"아파트를 찾지 못했습니다.");

    private final int errorCode;
    private final String errorMsg;

}
