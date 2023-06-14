INSERT INTO countries (code, name) VALUES
("BG", "BULGARIA"),
("RO", "ROMANIA"),
("TR", "TURKEY"),
("GR", "GREECE"),
("MK", "MACEDONIA"),
("RS", "SERBIA"),
("UK", "UNITED_KINGDOM"),
("US", "UNITED_STATES"),
("DE", "GERMANY"),
("FR", "FRANCE"),
("ES", "SPAIN"),
("IT", "ITALY"),
("RU", "RUSSIA"),
("CN", "CHINA"),
("KP", "NORTH_KOREA"),
("KR", "SOUTH_KOREA"),
("VN", "VIETNAM"),
("JP", "JAPAN");

INSERT INTO addresses (countryCode, city, street) VALUES 
("BG", "Sofia", "Rosario 1"),
("BG", "Plovdiv", "Chiprovets 5"),
("BG", "Burgas", "Drin 664"),
("RS", "Moscow", "Il'inka 13"),
("KP", "Pyongyang", "Sungri Street");

INSERT INTO customers (username, password, firstName, lastName, addressID) VALUES
("JustSasko", "thePassword", "Alexander", "Ivanov", 1),
("teacherMaximus", "Stefy<3", "Momchil", "Todorov", 1),
("rocketMan", "boomRocket", "Kim", "Jong-un", 5);
