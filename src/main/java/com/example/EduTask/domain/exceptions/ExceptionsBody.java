package com.example.EduTask.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionsBody {

    private String message;
    private Map<String,String> errors;

    public ExceptionsBody(
            final String message
    ){
        this.message=message;
    }
}
