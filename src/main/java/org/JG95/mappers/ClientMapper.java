package org.JG95.mappers;

import lombok.AllArgsConstructor;
import org.JG95.dtos.ClientDto;
import org.JG95.dtos.ClientWithContactsDto;
import org.JG95.dtos.ContactDto;
import org.JG95.entities.Client;
import org.JG95.entities.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@AllArgsConstructor
public class ClientMapper {

    private final ContactMapper contactMapper;

    public Client toClient(ClientDto clientDto) {
        return Client.builder()
                .clientId(clientDto.clientId())
                .name(clientDto.name())
                .build();
    }

    public ClientDto toClientDto(Client client) {
        return ClientDto.builder()
                .clientId(client.getClientId())
                .name(client.getName())
                .build();
    }

    public ClientWithContactsDto toClientWithContactsDto(Client client) {
        return ClientWithContactsDto.builder()
                .id(client.getClientId())
                .name(client.getName())
                .contacts(toContactsList(client))
                .build();
    }

    public List<ContactDto> toContactsList(Client client) {
        return getContactDtoList(getClientContacts(client));
    }

    public List<ContactDto> toPhonesList(Client client) {
        return getContactDtoList(getClientPhones(client));
    }

    public List<ContactDto> toEmailsList(Client client) {
        return getContactDtoList(getClientEmails(client));
    }

    private List<ContactDto> getContactDtoList(Collection<Contact> contacts) {
        return contacts.stream()
                .map(contactMapper::toContactDto)
                .toList();
    }

    private List<Contact> getClientContacts(Client client) {
        List<Contact> contacts = new ArrayList<>(client.getPhones());
        contacts.addAll(client.getEmails());
        return contacts;
    }

    private List<Contact> getClientPhones(Client client) {
        return new ArrayList<>(client.getPhones());
    }

    private List<Contact> getClientEmails(Client client) {
        return new ArrayList<>(client.getEmails());
    }
}
