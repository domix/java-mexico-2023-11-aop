package com.domingosuarez.talks.y2023.m11.javamexico.web.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class ErrorResponse {
    private String message;
}
