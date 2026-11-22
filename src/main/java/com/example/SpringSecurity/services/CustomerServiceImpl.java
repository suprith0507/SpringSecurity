package com.example.SpringSecurity.services;

import com.example.SpringSecurity.configs.MapstructUtility;
import com.example.SpringSecurity.dtos.CustomerRequestDTO;
import com.example.SpringSecurity.dtos.CustomerResponseDTO;
import com.example.SpringSecurity.entities.CustomerEntity;
import com.example.SpringSecurity.exceptions.CustomerAlreadyExistsException;
import com.example.SpringSecurity.exceptions.CustomerNotFoundException;
import com.example.SpringSecurity.repositories.CustomerRepository;
import com.example.SpringSecurity.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final MapstructUtility mapstructUtility;


    /**
     * ============================================================
     * GET ALL CUSTOMERS
     *
     * ENTITY -> RESPONSE DTO
     * USING MODELMAPPER
     * ============================================================
     */
    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository
                .findAll()
                .stream()
                .map(customerEntity ->
                        modelMapper.map(
                                customerEntity,
                                CustomerResponseDTO.class))
                .toList();
    }


    /**
     * ============================================================
     * GET CUSTOMER BY ID
     *
     * ENTITY -> RESPONSE DTO
     * USING MODELMAPPER
     * ============================================================
     */
    @Override
    public CustomerResponseDTO getCustomerById(
            Long customerId) {

        CustomerEntity customerEntity =
                customerRepository
                        .findById(customerId)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer Not Found With Id : "
                                                + customerId));

        return modelMapper.map(
                customerEntity,
                CustomerResponseDTO.class);
    }


    /**
     * ============================================================
     * INSERT CUSTOMER
     *
     * REQUEST DTO -> ENTITY
     * USING MAPSTRUCT
     * ============================================================
     */
    @Override
    public CustomerResponseDTO insertCustomer(
            CustomerRequestDTO customerRequestDTO) {

        if (customerRepository.existsByUsername(
                customerRequestDTO.getUsername())) {

            throw new CustomerAlreadyExistsException(
                    "Customer Already Exists With Username : "
                            + customerRequestDTO.getUsername());
        }

        CustomerEntity customerEntity =
                mapstructUtility.toEntity(
                        customerRequestDTO);

        customerEntity.setPassword(
                passwordEncoder.encode(
                        customerRequestDTO.getPassword()));

        CustomerEntity savedCustomer =
                customerRepository.save(
                        customerEntity);

        return modelMapper.map(
                savedCustomer,
                CustomerResponseDTO.class);
    }


    /**
     * ============================================================
     * UPDATE CUSTOMER DETAILS
     *
     * MODELMAPPER COPIES VALUES FROM DTO
     * INTO EXISTING ENTITY.
     *
     * Since:
     * modelMapper.setSkipNullEnabled(true)
     *
     * only non-null fields are updated.
     * ============================================================
     */
    @Override
    public CustomerResponseDTO updateCustomerDetails(
            Long customerId,
            CustomerRequestDTO customerRequestDTO) {

        CustomerEntity existingCustomer =
                customerRepository
                        .findById(customerId)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer Not Found With Id : "
                                                + customerId));

        modelMapper.map(
                customerRequestDTO,
                existingCustomer);

        if (customerRequestDTO.getPassword() != null
                && !customerRequestDTO.getPassword().isBlank()) {

            existingCustomer.setPassword(
                    passwordEncoder.encode(
                            customerRequestDTO.getPassword()));
        }

        CustomerEntity updatedCustomer =
                customerRepository.save(
                        existingCustomer);

        return modelMapper.map(
                updatedCustomer,
                CustomerResponseDTO.class);
    }


    /**
     * ============================================================
     * DELETE CUSTOMER BY ID
     * ============================================================
     */
    @Override
    public void deleteCustomerById(
            Long customerId) {

        CustomerEntity customerEntity =
                customerRepository
                        .findById(customerId)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer Not Found With Id : "
                                                + customerId));

        customerRepository.delete(
                customerEntity);
    }
}