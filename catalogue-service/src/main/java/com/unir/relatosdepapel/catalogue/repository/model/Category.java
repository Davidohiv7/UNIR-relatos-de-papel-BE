package com.unir.relatosdepapel.catalogue.repository.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    // Usamos FetchType.LAZY para que no cargue los libros en memoria a menos que se lo pidamos explícitamente
//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @JsonIgnoreProperties("category")
//    private List<Book> books;    // private List<Book> books;
}
