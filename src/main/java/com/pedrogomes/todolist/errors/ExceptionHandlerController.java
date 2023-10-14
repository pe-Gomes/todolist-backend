package com.pedrogomes.todolist.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlerController {
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException err) {
        return ResponseEntity.status(400).body(err.getMostSpecificCause().getMessage());
    }
}
