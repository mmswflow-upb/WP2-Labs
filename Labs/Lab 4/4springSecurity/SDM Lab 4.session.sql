USE lab4;
-- Create table for the User entity
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    enabled BOOLEAN DEFAULT TRUE
);
-- Create table for User roles (ElementCollection)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    roles INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
-- Create table for the Product entity
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);
-- Create initial users for testing (admin and normal user)
-- Note: In a real application, passwords should be encoded, not stored in plaintext
INSERT INTO user (username, password)
VALUES ('admin', 'admin');
INSERT INTO user (username, password)
VALUES ('user', 'user');
-- Assign roles to users
INSERT INTO user_roles (user_id, roles)
VALUES (1, 1);
-- ROLE_ADMIN for admin user
INSERT INTO user_roles (user_id, roles)
VALUES (2, 0);
-- ROLE_USER for normal user
-- Insert some sample products
INSERT INTO product (name)
VALUES ('Product 1');
INSERT INTO product (name)
VALUES ('Product 2');
INSERT INTO product (name)
VALUES ('Product 3');