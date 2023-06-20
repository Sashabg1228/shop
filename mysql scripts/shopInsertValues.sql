USE shop;

INSERT INTO countries (code, name, location, allowed) VALUES
('BG', 'BULGARIA', true, true),
('RU', 'RUSSIA', false, true),
('CN', 'CHINA', false, true),
('KP', 'NORTH_KOREA', false, false);

INSERT INTO addresses (countryCode, city, street) VALUES
('BG', 'Sofia', 'Rosario 1'),
('BG', 'Plovdiv', 'Chiprovets 5'),
('BG', 'Burgas', 'Drin 664'),
('RU', 'Moscow', 'Ilinka 13'),
('KP', 'Pyongyang', 'Sungri Street');

INSERT INTO customers (username, password, firstName, lastName, addressID) VALUES
('JustSasko', 'thePassword', 'Alexander', 'Ivanov', 1),
('teacherMaximus', 'Stefy<3', 'Momchil', 'Todorov', 1),
('rocketMan', 'boomRocket', 'Kim', 'Jong-un', 5);

INSERT INTO categories (code, name) VALUES
('P', 'PEN'),
('B', 'BOOK'),
('PA', 'PAPER'),
('ELE', 'ELECTRONICS'),
('PH', 'PHONE'),
('C', 'COMPUTER'),
('L', 'LAPTOP'),
('OTH', 'OTHER');

INSERT INTO products (name, price, quantity, weight, categoryCode, info) VALUES
('Red pen', 2.5, 15, 0.1, 'P', 'for teachers'),
('Green pen', 2, 20, 0.1, 'P', 'for principals'),
('Samsung S3 mini', 200, 5, 0.330, 'PH', 'Black Samsung S3 mini'),
('Nigga dad', 1000, 2, 65, 'OTH', 'Best workers');

INSERT INTO statuses (code, name) VALUES
('O', 'OPEN'),
('S', 'SHIPPED'),
('C', 'COMPLETED'),
('F', 'FAILED');

INSERT INTO orders (customerID, addressID, phone, creationDate, statusCode) VALUES (1, 1, '0898517008', NOW(), 'O');