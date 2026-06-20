package io.github.aryansh05.ticketing.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name cannot exceed {max} characters")
        @Pattern(
                regexp = "^[\\p{L} .'-]+$",
                message = "Name contains forbidden characters"
        )
        String fullName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Please provide a valid email address")
        @Size(max = 255, message = "Email address cannot exceed {max} characters")
        @Pattern(
                regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$",
                message = "Email format is invalid"
        )
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 255, message = "Password must be between {min} and {max} characters")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*]).*$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String password
) {
    public RegisterRequest {
        if (fullName != null) fullName = fullName.strip();
        if (email != null) email = email.strip().toLowerCase();
    }
}
