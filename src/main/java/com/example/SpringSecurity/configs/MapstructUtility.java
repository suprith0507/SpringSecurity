package com.example.SpringSecurity.configs;



import com.example.SpringSecurity.dtos.CustomerRequestDTO;
import com.example.SpringSecurity.dtos.CustomerResponseDTO;
import com.example.SpringSecurity.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapstructUtility {

    /**
     * =====================================================
     * REQUEST DTO -> ENTITY
     * =====================================================
     */
    CustomerEntity toEntity(
            CustomerRequestDTO customerRequestDTO);

    /**
     * =====================================================
     * ENTITY -> RESPONSE DTO
     * =====================================================
     */
    CustomerResponseDTO toDTO(
            CustomerEntity customer);
}
