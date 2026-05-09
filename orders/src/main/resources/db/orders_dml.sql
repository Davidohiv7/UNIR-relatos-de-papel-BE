-- Datos de prueba para orders
-- customer_id e id_catalogue son inventados, alinear con catalogue cuando este disponible

INSERT INTO orders (order_date, total, comment, status, customer_id) VALUES
                                                                         ('2026-01-10 10:00:00', 45.97, 'Primera compra',          'ENTREGADO',  1),
                                                                         ('2026-01-15 11:30:00', 29.99, NULL,                       'ENTREGADO',  1),
                                                                         ('2026-01-20 09:15:00', 89.95, 'Regalo de cumpleaños',     'ENVIADO',    1),
                                                                         ('2026-02-01 14:00:00', 15.50, NULL,                       'CANCELADO',  1),
                                                                         ('2026-02-14 16:45:00', 62.40, 'San Valentín',             'EN_PROCESO', 1),
                                                                         ('2026-01-05 08:00:00', 34.99, NULL,                       'ENTREGADO',  2),
                                                                         ('2026-01-25 12:00:00', 120.00,'Pedido grande',            'ENTREGADO',  2),
                                                                         ('2026-02-10 10:30:00', 49.99, NULL,                       'ENVIADO',    2),
                                                                         ('2026-03-01 09:00:00', 22.00, NULL,                       'EN_PROCESO', 3),
                                                                         ('2026-03-05 17:00:00', 75.80, 'Colección completa',       'EN_PROCESO', 3);

INSERT INTO order_item (order_id, id_catalogue, quantity, unit_price, sub_total) VALUES
-- Orden 1 (customer 1, total 45.97)
(1, 101, 2, 15.99, 31.98),
(1, 102, 1, 13.99, 13.99),
-- Orden 2 (customer 1, total 29.99)
(2, 103, 1, 29.99, 29.99),
-- Orden 3 (customer 1, total 89.95)
(3, 104, 3, 19.99, 59.97),
(3, 105, 2, 14.99, 29.98),
-- Orden 4 (customer 1, total 15.50)
(4, 106, 1, 15.50, 15.50),
-- Orden 5 (customer 1, total 62.40)
(5, 107, 2, 18.99, 37.98),
(5, 108, 1, 24.42, 24.42),
-- Orden 6 (customer 2, total 34.99)
(6, 101, 1, 15.99, 15.99),
(6, 109, 1, 19.00, 19.00),
-- Orden 7 (customer 2, total 120.00)
(7, 110, 4, 20.00, 80.00),
(7, 111, 2, 20.00, 40.00),
-- Orden 8 (customer 2, total 49.99)
(8, 112, 1, 49.99, 49.99),
-- Orden 9 (customer 3, total 22.00)
(9, 113, 2, 11.00, 22.00),
-- Orden 10 (customer 3, total 75.80)
(10, 114, 2, 25.00, 50.00),
(10, 115, 1, 25.80, 25.80);