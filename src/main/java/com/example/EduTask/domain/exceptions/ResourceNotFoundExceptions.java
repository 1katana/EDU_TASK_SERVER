package com.example.EduTask.domain.exceptions;

public class ResourceNotFoundExceptions extends RuntimeException {

    public ResourceNotFoundExceptions(
            final String message
    ){
        super(message);
    }

}
