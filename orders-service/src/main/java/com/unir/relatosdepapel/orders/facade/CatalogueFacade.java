package com.unir.relatosdepapel.orders.facade;

import com.unir.relatosdepapel.orders.facade.model.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

// TODO: Implementacion provisional con datos mock.
// Debe reemplazarse con llamadas reales al microservicio catalogue via Eureka.
// Usar WebClient con @LoadBalanced apuntando a http://catalogue-service/api/v1/books/{id}
@Component
@RequiredArgsConstructor
public class CatalogueFacade {

    public BookResponse getBook(Integer bookId) {
        // TODO: Reemplazar con WebClient call a catalogue, por ahora queda como Mock
        Random random = new Random();
        return BookResponse.builder()
                .id(bookId)
                .title("Book " + bookId)
                .author("Author " + bookId)
                .price(BigDecimal.valueOf(10 + random.nextInt(40)))
                .stock(random.nextInt(10) + 1)
                .visible(true)
                .build();
    }

    public void updateStock(Integer bookId, Integer newStock) {
        // TODO: Reemplazar con WebClient PATCH a catalogue
        // PATCH /books/{id} con Content-Type: application/merge-patch+json
        // body: { "stock": newStock }
    }
}