package com.dekapx.weatherapp.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
