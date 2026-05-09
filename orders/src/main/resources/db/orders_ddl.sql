CREATE TABLE orders (
                        id          INT AUTO_INCREMENT PRIMARY KEY,
                        order_date  TIMESTAMP      NOT NULL,
                        total       DECIMAL(10,2)  NOT NULL,
                        comment     TEXT,
                        status      ENUM('EN_PROCESO', 'CANCELADO', 'ENVIADO', 'ENTREGADO') NOT NULL DEFAULT 'EN_PROCESO',
                        customer_id INT            NOT NULL,
                        created_at  TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
                        updated_at  TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE order_item (
                            id           INT AUTO_INCREMENT PRIMARY KEY,
                            order_id     INT            NOT NULL,
                            id_catalogue INT            NOT NULL,
                            quantity     INT            NOT NULL CHECK (quantity > 0),
                            unit_price   DECIMAL(10,2)  NOT NULL,
                            sub_total    DECIMAL(10,2)  NOT NULL,
                            CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);