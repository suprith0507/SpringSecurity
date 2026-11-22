package com.example.SpringSecurity.services;



import com.example.SpringSecurity.dtos.CustomerRequestDTO;
import com.example.SpringSecurity.dtos.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    /**
     * ============================================================
     * GET ALL CUSTOMERS
     * ============================================================
     *
     * Returns all customers available in database.
     */
    List<CustomerResponseDTO> getAllCustomers();


    /**
     * ============================================================
     * GET CUSTOMER BY ID
     * ============================================================
     *
     * @param customerId Customer Primary Key
     * @return CustomerResponseDTO
     */
    CustomerResponseDTO getCustomerById(
            Long customerId);


    /**
     * ============================================================
     * INSERT CUSTOMER
     * ============================================================
     *
     * Creates a new customer.
     *
     * @param customerRequestDTO Request Payload
     * @return CustomerResponseDTO
     */
    CustomerResponseDTO insertCustomer(
            CustomerRequestDTO customerRequestDTO);


    /**
     * ============================================================
     * UPDATE CUSTOMER DETAILS
     * ============================================================
     *
     * Updates existing customer details.
     *
     * @param customerId Primary Key
     * @param customerRequestDTO Request Payload
     * @return CustomerResponseDTO
     */
    CustomerResponseDTO updateCustomerDetails(
            Long customerId,
            CustomerRequestDTO customerRequestDTO);


    /**
     * ============================================================
     * DELETE CUSTOMER BY ID
     * ============================================================
     *
     * Deletes customer from database.
     *
     * @param customerId Primary Key
     */
    void deleteCustomerById(
            Long customerId);
}