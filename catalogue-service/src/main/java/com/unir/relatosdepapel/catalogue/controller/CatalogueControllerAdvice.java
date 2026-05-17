package com.unir.relatosdepapel.catalogue.controller;

import com.unir.relatosdepapel.catalogue.controller.model.ErrorResponse;
import com.unir.relatosdepapel.catalogue.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CatalogueControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplyNotFound(BookNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .details(ex.getMessage())
                        .build());
    }
}



