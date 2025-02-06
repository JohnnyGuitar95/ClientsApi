package org.JG95.mappers;

import lombok.NoArgsConstructor;
import org.JG95.dtos.ContactDto;
import org.JG95.entities.Client;
import org.JG95.entities.Contact;
import org.JG95.entities.Email;
import org.JG95.entities.Phone;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ContactMapper {
    public Contact toPhone(ContactDto contactDto, Client client) {
        Contact phone = new Phone();
        phone.setClient(client);
        phone.setContactValue(contactDto.contactValue());
        return phone;
    }

    public Contact toEmail(ContactDto contactDto, Client client) {
        Contact email = new Email();
        email.setClient(client);
        email.setContactValue(contactDto.contactValue());
        return email;
    }

    public ContactDto toContactDto(Contact contact) {
        return ContactDto.builder()
                .contactId(contact.getContactId())
                .contactType(contact.getContactType())
                .contactValue(contact.getContactValue())
                .build();
    }
}
