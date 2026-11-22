package com.example.SpringSecurity.exceptions;

public class CustomerNotFoundException   extends RuntimeException{

    public CustomerNotFoundException(
            String message) {

        super(message);
    }

}
