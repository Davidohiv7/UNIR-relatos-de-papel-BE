package com.unir.relatosdepapel.catalogue.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "book", indexes = {
        @Index(name = "idx_title", columnList = "title"),
        @Index(name = "idx_author", columnList = "author"),
        @Index(name = "idx_category", columnList = "category_id"),
        @Index(name = "idx_featured", columnList = "featured")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "language", length = 50)
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false)
    private BookFormat format;

    @Column(name = "year")
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // <-- AGREGA ESTO
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // <-- AGREGA ESTO
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Builder.Default
    @Column(name = "stock")
    private Integer stock = 0;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Builder.Default
    @Column(name = "reviews_count")
    private Integer reviewsCount = 0;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "isbn", unique = true, length = 20)
    private String isbn;

    @Builder.Default
    @Column(name = "featured")
    private Boolean featured = false;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
