CREATE SEQUENCE users_seq START WITH 4 INCREMENT BY 1;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);


INSERT INTO users (id, name, email, password, role) VALUES
    (1, 'userRecep', 'userRecep@example.com', 'passwordR', 'R'),
    (2, 'userTech', 'userTech@example.com', 'passwordT', 'T'),
    (3, 'userTech2', 'userTech2@example.com', 'passwordT', 'T');
