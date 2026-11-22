package com.example.SpringSecurity.modelbeans;


import com.example.SpringSecurity.dtos.ErrorResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse<T> {

    private T data;

    private ErrorResponseDTO error;

    private LocalDateTime timestamp;
}
