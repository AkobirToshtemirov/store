package com.pdp.store.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponseDTO {
    String errorPath;
    Integer errorCode;
    Object errorBody;

    @Builder.Default
    LocalDateTime timestamp = LocalDateTime.now();
}
