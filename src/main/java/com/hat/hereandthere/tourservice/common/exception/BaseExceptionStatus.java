package com.hat.hereandthere.tourservice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseExceptionStatus {
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "Place not found"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error"),;
    private final HttpStatus status;
    private final String message;

    private BaseExceptionStatus(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}