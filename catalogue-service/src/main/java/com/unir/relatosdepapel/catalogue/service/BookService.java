package com.unir.relatosdepapel.catalogue.service;

import com.unir.relatosdepapel.catalogue.repository.model.Book;
import java.util.List;

public interface BookService {

    // 1. Búsqueda global flexible (MVP) o lista completa si el término es nulo
    List<Book> searchBooks(String searchTerm);

    // 2. Búsqueda de un libro en especial por su identificador único
    Book getBookById(Integer id);

    // 3. Obtener libros destacados para la portada del e-commerce
    List<Book> getFeaturedBooks();

    // 4. Método que operará detrás de la "Fachada" (Facade) para el microservicio de Órdenes
    // Devuelve un boolean para confirmar si la operación fue exitosa o falló por falta de stock
    boolean updateBookStock(Integer bookId, Integer quantityToSubstract);
}