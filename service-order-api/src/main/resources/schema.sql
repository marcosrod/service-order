CREATE SEQUENCE client_seq START WITH 2 INCREMENT BY 1;
CREATE SEQUENCE equipment_seq START WITH 2 INCREMENT BY 1;
CREATE SEQUENCE service_order_seq START WITH 2 INCREMENT BY 1;

CREATE TABLE client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE equipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL
);

CREATE TABLE service_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_receptionist BIGINT NOT NULL,
    fk_technician BIGINT NOT NULL,
    fk_client BIGINT NOT NULL,
    fk_equipment BIGINT NOT NULL,
    equipment_problem VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP,
    start_date TIMESTAMP,
    finish_date TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    service_details VARCHAR(255) NOT NULL,
    FOREIGN KEY(fk_client) REFERENCES client(id),
    FOREIGN KEY(fk_equipment) REFERENCES equipment(id)
);

INSERT INTO client (id, name, address, phone, email) VALUES
    (1, 'client', 'client address', 'client phone', 'client@email.com');

INSERT INTO equipment (id, type, model) VALUES
    (1, 'printer', 'HP');

INSERT INTO service_order (id, fk_receptionist, fk_technician, fk_client, fk_equipment,
 equipment_problem, creation_date, status, service_details) VALUES
    (1, 1, 1, 1, 1, 'problem to solve', CURRENT_TIMESTAMP, 'P', 'details of the service');

