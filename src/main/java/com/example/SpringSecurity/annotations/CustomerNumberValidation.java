package com.example.SpringSecurity.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented

@Constraint(
        validatedBy =
                CustomerNumberValidator.class
)

@Target({
        ElementType.FIELD
})

@Retention(
        RetentionPolicy.RUNTIME
)
public @interface CustomerNumberValidation {

    String message()
            default "Customer Number must follow format CUST followed by digits";

    Class<?>[] groups()
            default {};

    Class<? extends Payload>[] payload()
            default {};
}

