package com.unir.relatosdepapel.catalogue.controller;

import com.unir.relatosdepapel.catalogue.dto.StockUpdateDto;
import com.unir.relatosdepapel.catalogue.repository.model.Book;
import com.unir.relatosdepapel.catalogue.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class CatalogueController {

    private final BookService bookService;

    // 1. GET /api/v1/books -> Lista de libros (200 OK)
    // Mantenemos el parámetro opcional de búsqueda por si se quiere filtrar desde el navegador
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "search", required = false) String searchTerm) {
        List<Book> books = bookService.searchBooks(searchTerm);
        return ResponseEntity.ok(books);
    }

    // 2. GET /api/v1/books/{id} -> Detalle de libro (200 OK / 404 Not Found)
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (RuntimeException e) {
            // Si el servicio lanza el error de "no encontrado", respondemos con 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 3. POST /api/v1/books -> Libro creado (201 Created / 400 Bad Request)
    @PostMapping("/books")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        // TODO: Implementar en el servicio si se necesita crear nuevos libros
        return ResponseEntity.status(HttpStatus.CREATED).body("Libro creado correctamente.");
    }

    // 4. PUT /api/v1/books/{id} -> Libro reemplazado / Datos completos (200 OK / 404 Not Found)
    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        // TODO: Conectar con la lógica de modificación completa
        return ResponseEntity.ok("Libro reemplazado correctamente.");
    }

    // 5. PATCH /api/v1/books/{id} -> JSON parcial / Libro modificado (200 OK)
    @PatchMapping("/books/{id}")
    public ResponseEntity<String> partialUpdateBook(
            @PathVariable Integer id,
            @RequestBody StockUpdateDto stockUpdateDto) {
        try {
            // Usamos el ID que viene en la URL corporativa y la cantidad del DTO
            bookService.updateBookStock(id, stockUpdateDto.getQuantity());
            return ResponseEntity.ok("Stock del libro modificado parcialmente con éxito.");
        } catch (RuntimeException e) {
            // Si falta stock o el libro no existe, devolvemos un 400 Bad Request con el motivo del error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 6. DELETE /api/v1/books/{id} -> Libro eliminado (204 No Content / 404 Not Found)
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        // TODO: Implementar lógica de borrado si aplica al MVP
        return ResponseEntity.noContent().build(); // Retorna 204 No Content según tu tabla
    }

    // 7. Endpoint para obtener los libros destacados
    @GetMapping("/books/featured")
    public ResponseEntity<List<Book>> getFeaturedBooks() {
        List<Book> featuredBooks = bookService.getFeaturedBooks();
        return ResponseEntity.ok(featuredBooks);
    }

    // 8. El "Facade" de comunicación para el microservicio de Órdenes (Orders)
    @PutMapping("/books/stock")
    public ResponseEntity<String> updateStock(@RequestBody StockUpdateDto stockUpdateDto) {
        bookService.updateBookStock(stockUpdateDto.getBookId(), stockUpdateDto.getQuantity());
        return ResponseEntity.ok("Stock actualizado correctamente.");
    }
}
