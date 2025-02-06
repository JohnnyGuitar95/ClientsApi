package org.JG95.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record ContactDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of the contact")
        long contactId,

        @NotBlank(message = "Contact type should not be blank")
        @Schema(description = "Type of contact (e.g., EMAIL or PHONE)", example = "EMAIL")
        String contactType,

        @NotBlank(message = "Contact value should not be blank")
        @Schema(description = "Actual contact value (email or phone number)", example = "test@example.com")
        String contactValue) {
}