package org.JG95.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
@Builder

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long clientId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    private List<Phone> phones;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    private List<Email> emails = new ArrayList<>();
}