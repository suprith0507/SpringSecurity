package com.example.SpringSecurity.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomerNumberValidator
        implements ConstraintValidator<
        CustomerNumberValidation,
        String> {

    @Override
    public void initialize(
            CustomerNumberValidation constraintAnnotation) {

        /*
         * Optional
         * Runs once when validator initializes.
         */
    }

    @Override
    public boolean isValid(
            String customerNumber,

            ConstraintValidatorContext context) {

        /*
         * Let @NotBlank handle null/empty
         */

        if(customerNumber == null) {
            return true;
        }

        /*
         * Expected Formats:
         * CUST1001
         * CUST9999
         */

        return customerNumber.matches(
                "^CUST\\d+$"
        );
    }
}