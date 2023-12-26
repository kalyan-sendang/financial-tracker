package com.project.financialtracker.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseWrapper<T> {
    private int statusCode;
    private String message;
    private boolean success = false;
    private T response;
}