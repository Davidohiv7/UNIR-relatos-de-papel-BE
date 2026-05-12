# Relatos de Papel — Back-End

Back-end de la aplicación **Relatos de Papel** desarrollado con arquitectura de microservicios usando Java y Spring. Actividad 2 — Desarrollo Web: Full Stack, UNIR.

---

## Stack

| Tecnología | Uso |
|---|---|
| Java 25 | Lenguaje principal |
| Spring Boot | Base de cada microservicio |
| Spring Data JPA | Acceso a base de datos relacional |
| Spring Cloud Netflix Eureka | Descubrimiento y registro de servicios |
| Spring Cloud Gateway | Proxy inverso y enrutamiento dinámico |
| MySQL 8 | Bases de datos relacionales |
| Docker | Contenedorización |
| Maven | Build y gestión de dependencias |

---

## Estructura

```
UNIR-relatos-de-papel-BE/
├── eureka-server/
├── gateway/
├── catalogue-service/
├── orders-service/
└── sql/
    ├── catalogue/
    │   ├── catalogue_ddl.sql
    │   └── catalogue_dml.sql
    └── orders/
        ├── orders_ddl.sql
        └── orders_dml.sql
```

**`eureka-server/`**
Servidor de descubrimiento (Netflix Eureka). Todos los microservicios se registran aquí al arrancar. Hace posible que los servicios se localicen entre sí por nombre, sin necesidad de conocer IPs ni puertos.

**`gateway/`**
Punto de entrada único al back-end (Spring Cloud Gateway). Recibe todas las peticiones del cliente y las enruta dinámicamente al microservicio correcto consultando el registro de Eureka. Implementa transcripción de peticiones: el cliente siempre envía POST y el gateway lo transforma en el método HTTP que corresponde (GET, POST, PUT, PATCH o DELETE).

**`catalogue-service/`**
Microservicio de catálogo de libros. Gestiona el ciclo de vida completo de un libro: creación, consulta, modificación y eliminación. Soporta búsqueda dinámica combinando múltiples filtros (título, autor, categoría, ISBN, valoración, visibilidad). Tiene su propia base de datos independiente.

**`orders-service/`**
Microservicio de órdenes de compra. Recibe solicitudes de compra, consulta `catalogue-service` para validar que los libros existen, son visibles y tienen stock, persiste la orden y descuenta el stock mediante PATCH. Tiene su propia base de datos independiente.

**`sql/`**
Scripts SQL listos para ejecutar. `_ddl.sql` crea la estructura de tablas y `_dml.sql` inserta los datos de prueba necesarios para levantar el entorno.

---

## Arquitectura

```
  ╔══════════════════════════════════════════════════════════════════╗
  ║                           CLIENTE                                ║
  ║                     (navegador / Postman)                        ║
  ╚══════════════════════════╦═══════════════════════════════════════╝
                             ║  HTTP — todas las peticiones
                             ▼
  ╔══════════════════════════════════════════════════════════════════╗
  ║                    GATEWAY  · :8762                              ║
  ║                  Spring Cloud Gateway                            ║
  ║                                                                  ║
  ║   Recibe peticiones del cliente y las transforma al método       ║
  ║   HTTP correcto (GET · POST · PUT · PATCH · DELETE).             ║
  ║   Consulta Eureka para saber a qué instancia enrutar.            ║
  ╚══════════════╦═══════════════════════════════╦═══════════════════╝
                 ║                               ║
         /catalogue/**                      /orders/**
                 ║                               ║
                 ▼                               ▼
  ╔══════════════════════════╗   ╔══════════════════════════════════╗
  ║  catalogue-service       ║   ║  orders-service                  ║
  ║       :8080              ║   ║       :8081                      ║
  ║                          ║   ║                                  ║
  ║  Gestiona el catálogo    ║   ║  Registra compras de libros.     ║
  ║  de libros: crear,       ║◄──║  Antes de persistir una orden,   ║
  ║  buscar, modificar       ║──►║  consulta a catalogue-service    ║
  ║  y eliminar.             ║   ║  para validar stock y visibili-  ║
  ║                          ║   ║  dad, y luego descuenta stock    ║
  ║                          ║   ║  vía PATCH.                      ║
  ╚══════════════╦═══════════╝   ╚══════════════════╦═══════════════╝
                 ║                                  ║
                 ▼                                  ▼
  ┌──────────────────────────┐   ┌──────────────────────────────────┐
  │      DB Catálogo         │   │         DB Órdenes               │
  │    MySQL · :3306         │   │       MySQL · :3307              │
  └──────────────────────────┘   └──────────────────────────────────┘

  ╔══════════════════════════════════════════════════════════════════╗
  ║                  EUREKA SERVER  · :8761                          ║
  ║                  Netflix Eureka                                  ║
  ║                                                                  ║
  ║  Registro central de servicios. Gateway, catalogue-service y     ║
  ║  orders-service se registran aquí al arrancar. Permite que los   ║
  ║  servicios se encuentren entre sí por nombre, sin usar IPs ni    ║
  ║  puertos directos. El gateway consulta este registro para        ║
  ║  construir y actualizar su tabla de enrutamiento en tiempo real. ║
  ╚══════════════════════════════════════════════════════════════════╝
```

---

## Servicios

| Servicio | Puerto | Descripción |
|---|---|---|
| `eureka-server` | `8761` | Servidor de descubrimiento de servicios |
| `gateway` | `8762` | Proxy inverso con transcripción de peticiones |
| `catalogue-service` | `8080` | CRUD de libros y búsqueda dinámica |
| `orders-service` | `8081` | Registro y consulta de órdenes de compra |

### API — catalogue-service

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/v1/books` | Listar y filtrar libros |
| `GET` | `/api/v1/books/{id}` | Obtener un libro por ID |
| `POST` | `/api/v1/books` | Crear un libro |
| `PUT` | `/api/v1/books/{id}` | Reemplazar un libro |
| `PATCH` | `/api/v1/books/{id}` | Actualizar parcialmente un libro |
| `DELETE` | `/api/v1/books/{id}` | Eliminar un libro |

Filtros disponibles en `GET /api/v1/books` mediante query params combinables:
`title` · `author` · `publishDate` · `category` · `isbn` · `rating` · `visible`

### API — orders-service

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/orders` | Registrar una compra |
| `GET` | `/api/v1/orders?userId={id}` | Consultar órdenes recientes de un usuario |

---

## Ejecución en local

**1. Bases de datos**

```bash
docker run -d --name db-catalogue -e MYSQL_ROOT_PASSWORD=mysql -p 3306:3306 mysql:8
docker run -d --name db-orders    -e MYSQL_ROOT_PASSWORD=mysql -p 3307:3306 mysql:8
```

**2. Scripts SQL**

Ejecutar en orden los archivos de `sql/catalogue/` sobre `db-catalogue` y los de `sql/orders/` sobre `db-orders`.

**3. Servicios**

```bash
cd eureka-server     && mvn spring-boot:run
cd gateway           && mvn spring-boot:run
cd catalogue-service && mvn spring-boot:run
cd orders-service    && mvn spring-boot:run
```

**4. Verificación**

```
Eureka Dashboard   →   http://localhost:8761
Gateway            →   http://localhost:8762
```

---

## Variables de entorno

| Variable | Descripción |
|---|---|
| `EUREKA_URL` | URL del servidor Eureka (`http://localhost:8761/eureka`) |
| `DB_URL` | URL JDBC de la base de datos |
| `DB_USER` | Usuario de la base de datos |
| `DB_PASSWORD` | Contraseña de la base de datos |

Cada servicio define valores por defecto para entorno local en su `application.yml`.

---

## Equipo

| Integrante | Módulo |
|---|---|
| Anthony Robles | `eureka-server` + Dockerfiles |
| Cesar Rondón | `catalogue-service` |
| Christian Vivas | `orders-service` |
| Marcelo Changoluisa | `gateway` |
| Santiago Aguilar | Integración Docker + video-memoria |
