CREATE TABLE products (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          slug VARCHAR(255) NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(15, 2),
                          stock INT,
                          unit VARCHAR(50),
                          created_at BIGINT,
                          updated_at BIGINT,
                          deleted_at BIGINT
);
