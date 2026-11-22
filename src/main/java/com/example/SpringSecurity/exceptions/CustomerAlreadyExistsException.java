package com.example.SpringSecurity.exceptions;

public class CustomerAlreadyExistsException
        extends RuntimeException {

    public CustomerAlreadyExistsException(
            String message) {

        super(message);
    }}