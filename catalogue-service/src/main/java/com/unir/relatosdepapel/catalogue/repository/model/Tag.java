package com.unir.relatosdepapel.catalogue.repository.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    // Relación bidireccional: Un tag puede estar en muchos libros
//    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("books")
//    private List<Book> books;      //private List<Book> books;
}

