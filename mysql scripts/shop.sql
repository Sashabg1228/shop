CREATE DATABASE shop;
USE shop;

-- countries structure
CREATE TABLE countries (
    code VARCHAR(2) NOT NULL PRIMARY KEY,
    name VARCHAR(56) NOT NULL,
    location boolean NOT NULL,
    allowed boolean NOT NULL
);

-- address structure
CREATE TABLE addresses (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    countryCode VARCHAR(2) NOT NULL,
    city VARCHAR(25) NOT NULL,
    street VARCHAR(50) NOT NULL,
    FOREIGN KEY(countryCode) REFERENCES countries(code)
);

-- customer structure
CREATE TABLE customers (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    firstName VARCHAR(25) NOT NULL,
    lastName VARCHAR(25) NOT NULL,
    addressID INTEGER NOT NULL,
    FOREIGN KEY(addressID) REFERENCES addresses(id) ON DELETE CASCADE
);

-- category structure
CREATE TABLE categories (
    code VARCHAR(3) NOT NULL PRIMARY KEY,
    name VARCHAR(15) NOT NULL
);

-- products structure
CREATE TABLE products (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(6,2) NOT NULL,
    quantity INTEGER NOT NULL,
    weight NUMERIC(6,3) NOT NULL,
    categoryCode VARCHAR(3) NOT NULL,
    info VARCHAR(200),
    FOREIGN KEY(categoryCode) REFERENCES categories(code) ON DELETE CASCADE
);

-- status reference
CREATE TABLE statuses (
    code VARCHAR(1) NOT NULL PRIMARY KEY,
    name VARCHAR(9)
);

-- orders reference
CREATE TABLE orders (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    customerID INTEGER NOT NULL,
    addressID INTEGER NOT NULL,
    phone VARCHAR(10) NOT NULL,
    creationDate DATETIME NOT NULL,
    statusCode VARCHAR(1) NOT NULL,
    FOREIGN KEY(customerID) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY(addressID) REFERENCES addresses(id),
    FOREIGN KEY(statusCode) REFERENCES statuses(code)
);

-- products connection to orders
CREATE TABLE orderedProducts (
    id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
    orderID INTEGER NOT NULL,
    productID INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    FOREIGN KEY(orderID) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY(productID) REFERENCES products(id) ON DELETE CASCADE
);
