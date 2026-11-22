package com.example.SpringSecurity.controllers;

import com.example.SpringSecurity.dtos.CustomerRequestDTO;
import com.example.SpringSecurity.dtos.CustomerResponseDTO;
import com.example.SpringSecurity.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>>
    getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO>
    getCustomerById(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                customerService.getCustomerById(
                        customerId));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO>
    insertCustomer(

            @Valid

            @RequestBody
            CustomerRequestDTO customerRequestDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        customerService.insertCustomer(
                                customerRequestDTO));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO>
    updateCustomerDetails(

            @PathVariable Long customerId,

            @Valid

            @RequestBody
            CustomerRequestDTO customerRequestDTO) {

        return ResponseEntity.ok(
                customerService.updateCustomerDetails(
                        customerId,
                        customerRequestDTO));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String>
    deleteCustomerById(
            @PathVariable Long customerId) {

        customerService.deleteCustomerById(
                customerId);

        return ResponseEntity.ok(
                "Customer Deleted Successfully");
    }
}