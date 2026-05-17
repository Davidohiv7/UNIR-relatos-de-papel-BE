package com.unir.relatosdepapel.catalogue.repository;

import com.unir.relatosdepapel.catalogue.repository.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Método de búsqueda personalizado aprovechando los índices del DDL
    List<Book> findByAuthor(String author, Pageable pageable);

    List<Book> findByCategoryId(Integer categoryId, Pageable pageable);

    List<Book> findByFeaturedTrue(Pageable pageable);

    // Permite buscar libros cuyo título contenga una palabra clave (ej: "Quijote") sin importar mayúsculas
    List<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Una búsqueda combinada inteligente por si el usuario escribe algo en un buscador general
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);





}
