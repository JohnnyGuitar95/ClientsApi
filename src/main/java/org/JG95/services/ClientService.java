package org.JG95.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.JG95.advices.annotations.LoggingExecAfterThrow;
import org.JG95.advices.annotations.LoggingExecAround;
import org.JG95.dtos.ClientDto;
import org.JG95.dtos.ClientWithContactsDto;
import org.JG95.dtos.ContactDto;
import org.JG95.entities.Client;
import org.JG95.entities.Contact;
import org.JG95.entities.ContactType;
import org.JG95.exceptions.ClientNotFoundException;
import org.JG95.exceptions.DuplicateDataException;
import org.JG95.exceptions.InvalidContactType;
import org.JG95.mappers.ClientMapper;
import org.JG95.mappers.ContactMapper;
import org.JG95.repositories.ClientRepository;
import org.JG95.repositories.ContactRepository;
import org.JG95.utils.ContactValidationService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;
    private final ContactValidationService contactValidationService;

    @LoggingExecAround
    public ClientDto createClient(ClientDto clientDto) {
        log.info("Creating client: {}", clientDto.name());
        Client client = clientRepository.save(clientMapper.toClient(clientDto));
        return clientMapper.toClientDto(client);
    }

    @LoggingExecAround
    @LoggingExecAfterThrow
    @Cacheable("clients")
    public ClientWithContactsDto getClient(long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toClientWithContactsDto)
                .orElseThrow(() -> {
                    log.error("Client with id {} not found", id);
                    return new ClientNotFoundException("Client with id " + id + " not found");
                });
    }

    @LoggingExecAround
    @LoggingExecAfterThrow
    public Iterable<ContactDto> findAllContacts(long id, String contactType) {
        log.info("Finding all contacts with id {} and type {}", id, contactType);
        Client client = getClientById(id);

        if (contactType == null || contactType.isBlank()) {
            return clientMapper.toContactsList(client);
        }
        return switch (ContactType.fromContactValue(contactType)
                .orElseThrow(() -> new InvalidContactType("Incorrect contact type: " + contactType + ". Should be EMAIL or PHONE "))) {
            case PHONE -> clientMapper.toPhonesList(client);
            case EMAIL -> clientMapper.toEmailsList(client);
        };
    }

    @LoggingExecAround
    public Iterable<ClientDto> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toClientDto)
                .toList();
    }

    @LoggingExecAround
    @LoggingExecAfterThrow
    public ContactDto createContact(long id, ContactDto contactDto) {
        Client client = getClientById(id);

        if (contactRepository.existsByClientAndContactValue(client, contactDto.contactValue())) {
            log.warn("Contact {} already exists for client {}", contactDto.contactValue(), id);
            throw new DuplicateDataException("Contact " + contactDto.contactValue() + " already exists for this client");
        }
        Contact contact = defineContact(contactDto, client);
        contactRepository.save(contact);
        return contactMapper.toContactDto(contact);

    }

    @LoggingExecAround
    private Client getClientById (long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with id " + id + " not found"));
    }

    @LoggingExecAround
    @LoggingExecAfterThrow
    private Contact defineContact(ContactDto contactDto, Client client) {
        return switch (ContactType.fromContactValue(contactDto.contactType())
                .orElseThrow(() -> new InvalidContactType("Incorrect contact type: " + contactDto.contactType()))) {
            case PHONE -> {
                contactValidationService.validatePhone(contactDto.contactValue());
                yield contactMapper.toPhone(contactDto, client);
            }
            case EMAIL -> {
                contactValidationService.validateEmail(contactDto.contactValue());
                yield contactMapper.toEmail(contactDto, client);
            }
        };
    }


}

