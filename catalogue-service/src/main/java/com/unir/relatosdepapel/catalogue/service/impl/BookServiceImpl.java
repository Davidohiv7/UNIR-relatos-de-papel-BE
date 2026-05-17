package com.unir.relatosdepapel.catalogue.service.impl;

import com.unir.relatosdepapel.catalogue.repository.BookRepository;
import com.unir.relatosdepapel.catalogue.repository.model.Book;
import com.unir.relatosdepapel.catalogue.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok genera el constructor con el BookRepository automáticamente
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String searchTerm) {
        // Si el buscador está vacío, usamos Pageable.unpaged() para traer todos los libros
        if (!StringUtils.hasText(searchTerm)) {
            return bookRepository.findAll();
        }
        // Si hay texto, usamos la búsqueda combinada ignorando mayúsculas/minúsculas
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                searchTerm, searchTerm, Pageable.unpaged()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Integer id) {
        // Buscamos el libro. Si no existe, lanzamos una excepción en tiempo de ejecución
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con el ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getFeaturedBooks() {
        return bookRepository.findByFeaturedTrue(Pageable.unpaged());
    }

    @Override
    @Transactional // Sin readOnly, porque aquí SÍ vamos a modificar datos (UPDATE)
    public boolean updateBookStock(Integer bookId, Integer quantityToSubtract) {
        // 1. Buscar el libro
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar el stock. Libro no encontrado."));

        // 2. Validar si hay suficiente stock para la venta
        if (book.getStock() < quantityToSubtract) {
            throw new RuntimeException("Stock insuficiente para el libro: " + book.getTitle()
                    + ". Stock actual: " + book.getStock() + ", Solicitado: " + quantityToSubtract);
        }

        // 3. Restar el stock y guardar
        book.setStock(book.getStock() - quantityToSubtract);
        bookRepository.save(book); // JPA ejecuta el UPDATE tras bambalinas

        return true;
    }
}