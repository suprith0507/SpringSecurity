package com.example.SpringSecurity.dtos;

import com.example.SpringSecurity.annotations.CustomerNumberValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
/****************************************************************************************
 * CUSTOMER REQUEST DTO
 *
 * Used For:
 * ✅ Customer Registration
 * ✅ Create Customer API
 * ✅ Update Customer API
 *
 * Password is required here because user is sending it to us.
 ****************************************************************************************/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
public class CustomerRequestDTO {

    @NotBlank(message = "Customer Number is mandatory")
    @Size(min = 5, max = 20,
            message = "Customer Number should be between 5 and 20 characters")
    @CustomerNumberValidation
    private String customerNumber;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, max = 20,
            message = "Username should be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 20,
            message = "Password should be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "Full Name is mandatory")
    @Size(min = 3, max = 50,
            message = "Full Name should be between 3 and 50 characters")
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid Email Format")
    private String email;

    @NotBlank(message = "Mobile Number is mandatory")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile Number must contain exactly 10 digits"
    )
    private String mobileNumber;

    @NotBlank(message = "Role is mandatory")
    private String role;
}

