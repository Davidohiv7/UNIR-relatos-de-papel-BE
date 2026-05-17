-- catalogue_ddl.sql - MVP Mínimo
-- Base de datos para catálogo de libros

CREATE DATABASE IF NOT EXISTS db_catalogue;
USE db_catalogue;

-- Tabla de categorías
CREATE TABLE category (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de etiquetas
CREATE TABLE tag (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de libros
CREATE TABLE book (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      description TEXT,
                      language VARCHAR(50),
                      format ENUM('FISICO', 'DIGITAL') NOT NULL DEFAULT 'FISICO',
                      year INT,
                      category_id INT NOT NULL,
                      tag_id INT,
                      price DECIMAL(10, 2),
                      stock INT DEFAULT 0,
                      rating DECIMAL(3, 2) CHECK (rating >= 1.0 AND rating <= 5.0),
                      reviews_count INT DEFAULT 0,
                      pages INT,
                      isbn VARCHAR(20) UNIQUE,
                      featured BOOLEAN DEFAULT FALSE,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                      FOREIGN KEY (category_id) REFERENCES category(id),
                      FOREIGN KEY (tag_id) REFERENCES tag(id),

                      INDEX idx_title (title),
                      INDEX idx_author (author),
                      INDEX idx_category (category_id),
                      INDEX idx_featured (featured)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;