package com.example.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnsuccessfulScheduling extends RuntimeException {
    public UnsuccessfulScheduling(String message) {
        super(message);
    }
}
