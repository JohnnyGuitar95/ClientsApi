package org.JG95.entities;

import java.util.Arrays;
import java.util.Optional;

public enum ContactType {
    PHONE("phone"),
    EMAIL("email");

    private final String contactValue;

    ContactType(String contactValue) {
        this.contactValue = contactValue;
    }

    public static Optional<ContactType> fromContactValue(String contactValue) {
        return Arrays.stream(values())
                .filter(contactType -> contactType.contactValue.equalsIgnoreCase(contactValue))
                .findFirst();
    }
}
