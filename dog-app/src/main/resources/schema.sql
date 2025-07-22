CREATE TABLE dog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    breed VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    weight DOUBLE NOT NULL,
    existing_medical_conditions BOOLEAN NOT NULL
);
