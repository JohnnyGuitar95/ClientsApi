package org.JG95.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EMAIL")
public class Email extends Contact {
    @Override
    public String getContactType() {
        return ContactType.EMAIL.name();
    }
}
