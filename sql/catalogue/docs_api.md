# Especificación de API: Microservicio Catalogue

BooksController — /api/v1/

## Endpoints de Libros (/books)

| Método | Endpoint             | Cuerpo (JSON)   | Respuesta         | Código                      |
| :----- | :------------------- | :-------------- | :---------------- | :-------------------------- |
| GET    | `/api/v1/books`      | N/A             | Lista de libros   | 200 OK                      |
| GET    | `/api/v1/books/{id}` | N/A             | Detalle de libro  | 200 OK/404 Not Found        |
| POST   | `/api/v1/books`      | Datos del libro | Libro creado      | 201 Created/400 Bad Request |
| PUT    | `/api/v1/books/{id}` | Datos completos | Libro reemplazado | 200 OK/404 Not Found        |
| PATCH  | `/api/v1/books/{id}` | JSON parcial    | Libro modificado  | 200 OK                      |
| DELETE | `/api/v1/books/{id}` | N/A             | libro eliminado   | 204 No Content/404 Not      |


Ejemplo de book.

Json
{
   id: 1,
   title: 'Cien años de soledad',
   author: 'Gabriel García Márquez',
   description: 'Una obra maestra de la literatura latinoamericana que cuenta la historia de varias generaciones en un pueblo ficticio',
   language: 'Español',      
   format: BookFormat.Físico,
   year: 1967,
   categories: [
      {
         id: 1,
         name: 'Ficción',
      },
   ],
   tag: 1
   price: 15.99,
   stock: 50,
   rating: 4.8,
   reviews_count: 120,
   pages: 417,
   isbn: '978-0-06-085423-8',  
   featured: true,
}

