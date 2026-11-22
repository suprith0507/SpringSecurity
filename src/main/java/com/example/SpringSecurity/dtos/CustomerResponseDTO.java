package com.example.SpringSecurity.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {

    private Long customerId;

    private String customerNumber;

    private String username;

    private String fullName;

    private String email;

    private String mobileNumber;

    private String role;

    private boolean enabled;
}
