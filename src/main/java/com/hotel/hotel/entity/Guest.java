package com.hotel.hotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "guest")
@NoArgsConstructor
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false, unique = false)
    private String name;

    @NotBlank
    @Column(name = "document", nullable = false, unique = true)
    private String document;

    @NotBlank
    @Column(name = "telephone", nullable = false, unique = true)
    private String telephone;

    @Builder
    public Guest(String name, String document, String telephone) {
        this.name = name;
        this.document = document;
        this.telephone = telephone;
    }
}
