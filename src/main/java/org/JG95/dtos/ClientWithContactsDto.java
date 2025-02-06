package org.JG95.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record ClientWithContactsDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unique identifier of the client")
        long id,

        @NotBlank(message = "Name should not be blank")
        @Schema(description = "The name of the client", example = "John Doe")
        String name,

        @Schema(description = "List of contacts associated with the client")
        List<ContactDto> contacts) {
}