package org.JG95.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contact_type", discriminatorType = DiscriminatorType.STRING)

public abstract class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "contact_id")
    private long contactId;
    @Column(nullable = false)
    private String contactValue;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clientId", nullable = false)
    private Client client;

    public abstract String getContactType();

}
