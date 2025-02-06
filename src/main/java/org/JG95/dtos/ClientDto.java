package org.JG95.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
public record ClientDto(

        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of the client")
        long clientId,

        @NotBlank(message = "Name should not be blank")
        @Size(min = 1, max = 100, message = "Name should be between 1 and 100 characters")
        @Schema(description = "The name of the client", example = "John Doe")
        String name) {
}