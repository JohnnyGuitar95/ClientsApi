package org.JG95.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PHONE")
public class Phone extends Contact {
    @Override
    public String getContactType() {
        return ContactType.PHONE.name();
    }
}
