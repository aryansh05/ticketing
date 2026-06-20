package io.github.aryansh05.ticketing.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        boolean success,
        int status,
        String error,
        String message,
        Map<String, String> validationErrors
) {

}
