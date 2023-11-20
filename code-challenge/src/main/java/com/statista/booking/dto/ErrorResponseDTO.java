package com.statista.booking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class ErrorResponseDTO {

    private final String message;

    public ErrorResponseDTO(String message) {
        this.message = message;
    }
}