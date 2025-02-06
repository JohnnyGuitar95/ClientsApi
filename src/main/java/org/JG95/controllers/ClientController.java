package org.JG95.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.JG95.dtos.ClientDto;
import org.JG95.dtos.ClientWithContactsDto;
import org.JG95.dtos.ContactDto;
import org.JG95.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-v1/clients")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clients", description = "Management of clients and their contacts")
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "Get client by ID", description = "Returns a client with their contacts by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientWithContactsDto.class))),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientWithContactsDto> findClientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(clientService.getClient(id));
    }

    @Operation(summary = "Get client contacts", description = "Returns a list of client contacts by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contacts found",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ContactDto.class)))),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}/contacts")
    public ResponseEntity<Iterable<ContactDto>> findContactsByClientId(
            @PathVariable("id") Long id,
            @Parameter(description = "Filter by contact type") @RequestParam(required = false, name = "contactType") String contactType) {
        log.info("Find contacts by id: {}", id);
        return ResponseEntity.ok().body(clientService.findAllContacts(id, contactType));
    }

    @Operation(summary = "Get all clients", description = "Returns a list of all clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of clients",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ClientDto.class))))
    })
    @GetMapping
    public ResponseEntity<Iterable<ClientDto>> getAllClients() {
        return ResponseEntity.ok().body(clientService.findAllClients());
    }

    @Operation(summary = "Create a client", description = "Creates a new client and returns their data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientDto));
    }

    @Operation(
            summary = "Add a new contact for a client",
            description = "Creates a new contact (email or phone) for the specified client. Returns 409 Conflict if the contact already exists.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Contact created successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "409", description = "Contact already exists")
            }
    )
    @PostMapping("/{id}/contacts")
    public ResponseEntity<ContactDto> addContact(
            @PathVariable("id") Long id,
            @Valid @RequestBody ContactDto contactDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createContact(id, contactDto));
    }
}

