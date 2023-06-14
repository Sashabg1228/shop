import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class MySQLdb {
    private static final String url = "jdbc:mysql://localhost:3306/shop";
    private static final String username = "root";
    private static final String password = "ThePassword4BS!";

    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    public static void restartDataBase() {
        cleanDataBase();
        initializeDataBase();
        uploadDefaultData();
        showDataBase();
    }

    public static void cleanDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();
            try {
                statement.execute("DROP DATABASE shop;");
            } catch (Exception e) {
                System.out.println("Drop Database exception: " + e);
            }
            statement.execute("CREATE DATABASE shop;");

            connect.commit();
        } catch (Exception e) {
            System.out.println("Clean Database exception: " + e);
        } finally {
            close();
        }
    }

    public static void initializeDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = """
                    CREATE TABLE countries (
                      code VARCHAR(2) NOT NULL PRIMARY KEY,
                      name VARCHAR(56) NOT NULL,
                      location boolean NOT NULL,
                      allowed boolean NOT NULL
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE addresses (
                      id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      countryCode VARCHAR(2) NOT NULL,
                      city VARCHAR(25) NOT NULL,
                      street VARCHAR(50) NOT NULL,
                      FOREIGN KEY(countryCode) REFERENCES countries(code)
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE customers (
                      id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      username VARCHAR(25) NOT NULL,
                      password VARCHAR(25) NOT NULL,
                      firstName VARCHAR(25) NOT NULL,
                      lastName VARCHAR(25) NOT NULL,
                      addressID INTEGER NOT NULL,
                      FOREIGN KEY(addressID) REFERENCES addresses(id) ON DELETE CASCADE
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE category (
                      code VARCHAR(3) NOT NULL PRIMARY KEY,
                      name VARCHAR(15) NOT NULL
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE color (
                    	code VARCHAR(3) NOT NULL PRIMARY KEY,
                      name VARCHAR(15) NOT NULL
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE products (
                      id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      price NUMERIC(6,2) NOT NULL,
                      quantity INTEGER NOT NULL,
                      weight NUMERIC(6,3) NOT NULL,
                      categoryCode VARCHAR(3) NOT NULL,
                      colorCode VARCHAR(3) NOT NULL,
                      info VARCHAR(200),
                      FOREIGN KEY(categoryCode) REFERENCES category(code) ON DELETE CASCADE,
                      FOREIGN KEY(colorCode) REFERENCES color(code) ON DELETE CASCADE
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE status (
                      code VARCHAR(1) NOT NULL PRIMARY KEY,
                      name VARCHAR(9)
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE orders (
                      id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      customerID INTEGER NOT NULL,
                      addressID INTEGER NOT NULL,
                      phone VARCHAR(10) NOT NULL,
                      statusCode VARCHAR(1) NOT NULL,
                      FOREIGN KEY(customerID) REFERENCES customers(id) ON DELETE CASCADE,
                      FOREIGN KEY(addressID) REFERENCES addresses(id),
                      FOREIGN KEY(statusCode) REFERENCES status(code)
                    );""";
            statement.execute(mysql);

            mysql = """
                    CREATE TABLE orderedProducts (
                      id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
                      orderID INTEGER NOT NULL,
                      productID INTEGER NOT NULL,
                      quantity INTEGER NOT NULL,
                      FOREIGN KEY(orderID) REFERENCES orders(id) ON DELETE CASCADE,
                      FOREIGN KEY(productID) REFERENCES products(id) ON DELETE CASCADE
                    );""";
            statement.execute(mysql);

            connect.commit();
        } catch (Exception e) {
            System.out.println("Initialize Data Base exception: " + e);
        } finally {
            close();
        }
    }

    public static void showDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            preparedStatement = connect.prepareStatement("show tables;");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String table = resultSet.getString("Tables_in_shop");

                System.out.println("table: " + table);
            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Show Data Base exception: " + e);
        } finally {
            close();
        }
    }

    public static void showCountries() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            preparedStatement = connect.prepareStatement("SELECT countries.name FROM countries;");
            resultSet = preparedStatement.executeQuery();

            System.out.print("Countries list: ");
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                System.out.print(" " + name + ";");

            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Show Countries exception: " + e);
        } finally {
            close();
        }
    }

    public static void showAddresses() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            String mysql = """
                    SELECT *
                    FROM addresses
                    LEFT JOIN countries
                    ON addresses.countryCode = countries.code;
                    """;

            preparedStatement = connect.prepareStatement(mysql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String countryCode = resultSet.getString("countries.name");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");

                System.out.println("id: " + id);
                System.out.println("countryCode: " + countryCode);
                System.out.println("city: " + city);
                System.out.println("street: " + street);
                System.out.println();
            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Show Addresses exception: " + e);
        } finally {
            close();
        }
    }

    public static void showCategory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            preparedStatement = connect.prepareStatement("SELECT category.name FROM category;");
            resultSet = preparedStatement.executeQuery();

            System.out.print("category list: ");
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                System.out.print(" " + name + ";");

            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Show category exception: " + e);
        } finally {
            close();
        }
    }

    public static void showColor() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            preparedStatement = connect.prepareStatement("SELECT color.name FROM color;");
            resultSet = preparedStatement.executeQuery();

            System.out.print("color list: ");
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                System.out.print(" " + name + ";");

            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Show Color exception: " + e);
        } finally {
            close();
        }
    }

    public static void showProducts() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            String mysql = """
                    SELECT products.name, products.price, products.quantity, products.weight, category.name, color.name, products.info
                    FROM products
                    LEFT JOIN category
                    ON products.categoryCode = category.code
                    LEFT JOIN color
                    ON products.colorCode = color.code;
                    """;

            preparedStatement = connect.prepareStatement(mysql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("products.name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double weight = resultSet.getDouble("weight");
                String category = resultSet.getString("category.name");
                String color = resultSet.getString("color.name");
                String info = resultSet.getString("info");

                System.out.println("name: " + name);
                System.out.println("price: " + price);
                System.out.println("quantity: " + quantity);
                System.out.println("weight: " + weight);
                System.out.println("category: " + category);
                System.out.println("color: " + color);
                System.out.println("info: " + info);
                System.out.println();
            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Show Products exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadDefaultData() {
        uploadCoutry("BG", "BULGARIA", true, true);
        uploadCoutry("RU", "RUSSIA", false, true);
        uploadCoutry("CN", "CHINA", false, true);
        uploadCoutry("KP", "NORTH_KOREA", false, false);

        uploadAddress("BG", "Sofia", "Rosario 1");
        uploadAddress("BG", "Plovdiv", "Chiprovets 5");
        uploadAddress("BG", "Burgas", "Drin 664");
        uploadAddress("RU", "Moscow", "Ilinka 13");
        uploadAddress("KP", "Pyongyang", "Sungri Street");

        uploadCustomer("JustSasko", "thePassword", "Alexander", "Ivanov", 1);
        uploadCustomer("teacherMaximus", "Stefy<3", "Momchil", "Todorov", 1);
        uploadCustomer("rocketMan", "boomRocket", "Kim", "Jong-un", 5);

        uploadStatus("O", "OPEN");
        uploadStatus("S", "SHIPPED");
        uploadStatus("C", "COMPLETED");
        uploadStatus("F", "FAILED");

        uploadCategory("P", "PEN");
        uploadCategory("B", "BOOK");
        uploadCategory("PA", "PAPER");
        uploadCategory("ELE", "ELECTRONICS");
        uploadCategory("PH", "PHONE");
        uploadCategory("C", "COMPUTER");
        uploadCategory("L", "LAPTOP");
        uploadCategory("OTH", "OTHER");

        uploadColor("Y", "YELLOW");
        uploadColor("P", "PINK");
        uploadColor("V", "VIOLET");
        uploadColor("G", "GREEN");
        uploadColor("R", "RED");
        uploadColor("B", "BLACK");
        uploadColor("W", "WHITE");

        uploadProduct("Red pen", 2.5, 15, 0.1, "P", "R", "for teachers");
        uploadProduct("Green pen", 2, 20, 0.1, "P", "G", "for principals");
        uploadProduct("Samsung S3 mini", 200, 5, 0.330, "PH", "B", "Black Samsung S3 mini");
        uploadProduct("Nigga dad", 1000, 2, 65, "OTH", "B", "Best workers");
    }

    public static void uploadCoutry(final String countryCode, final String countryName,
                                    final boolean location, final boolean allowed) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO countries (code, name, location, allowed) VALUES (?, ?, ?, ?);";
            preparedStatement = connect.prepareStatement(mysql);

            preparedStatement.setString(1, countryCode);
            preparedStatement.setString(2, countryName);
            preparedStatement.setBoolean(3, location);
            preparedStatement.setBoolean(4, allowed);

            preparedStatement.executeUpdate();
            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload Country exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadAddress(final String countryCode, final String city, final String street) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO addresses (countryCode, city, street) VALUES (?, ?, ?)";
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, countryCode);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            preparedStatement.executeUpdate();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload Address exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadCustomer(final String usernameC, final String passwordC,
                                      final String firstName, final String lastName, final int addressID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO customers (username, password, firstName, lastName, addressID) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connect.prepareStatement(mysql);

            preparedStatement.setString(1, usernameC);
            preparedStatement.setString(2, passwordC);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5, addressID);

            preparedStatement.executeUpdate();
            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload User exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadStatus(final String statusCode, final String statusName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO status (code, name) VALUES (?, ?)";
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, statusCode);
            preparedStatement.setString(2, statusName);
            preparedStatement.executeUpdate();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload Status exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadCategory(final String categoryCode, final String categoryName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO category (code, name) VALUES (?, ?)";
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, categoryCode);
            preparedStatement.setString(2, categoryName);
            preparedStatement.executeUpdate();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload Category exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadColor(final String colorCode, final String colorName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO color (code, name) VALUES (?, ?)";
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, colorCode);
            preparedStatement.setString(2, colorName);
            preparedStatement.executeUpdate();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload Color exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadOrder(final int customerID, final int addressID, final String phone,
                                   final String statusCode) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO orders (customerID, addressID, phone, statusCode) VALUES (?, ?, ?, ?)";
            preparedStatement = connect.prepareStatement(mysql);

            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, addressID);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, statusCode);

            preparedStatement.executeUpdate();
            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload Order exception: " + e);
        } finally {
            close();
        }
    }

    public static void uploadProduct(final String name, final double price, final int quantity, final double weight,
                                     final String categoryCode, final String colorCode, final String info) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            String mysql = "INSERT INTO products (name, price, quantity, weight, categoryCode, colorCode, info) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connect.prepareStatement(mysql);

            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, weight);
            preparedStatement.setString(5, categoryCode);
            preparedStatement.setString(6, colorCode);
            preparedStatement.setString(7, info);

            preparedStatement.executeUpdate();
            connect.commit();
        } catch (Exception e) {
            System.out.println("Upload Product exception: " + e);
        } finally {
            close();
        }
    }

    public static boolean allowCountry(final int addressID) {
        boolean result = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT countries.allowed
                    FROM addresses
                    LEFT JOIN countries
                    ON addresses.countryCode = countries.code
                    WHERE addresses.ID = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setInt(1, addressID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getBoolean("countries.allowed");
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Check Country exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static String checkCountry(final String countryName) {
        String result = "--"; // -- = not found, else id

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT countries.code
                    FROM countries
                    WHERE countries.name = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, countryName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getString("code");
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Check Country exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static String checkCategory(final String categoryName) {
        String result = "--"; // -- = not found, else id

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT category.code
                    FROM category
                    WHERE category.name = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, categoryName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getString("code");
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Check Category exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static String checkColor(final String colorName) {
        String result = "--"; // -- = not found, else id

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT color.code
                    FROM color
                    WHERE color.name = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, colorName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getString("code");
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Check Color exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static Address loadAddress(final String countryCode, final String city, final String street) {
        Address result = new Address();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT *
                    FROM addresses
                    WHERE addresses.countryCode = ? AND addresses.city = ? AND addresses.street = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, countryCode);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result.setID(resultSet.getInt("id"));
                result.setCountryCode(resultSet.getString("countryCode"));
                result.setCity(resultSet.getString("city"));
                result.setStreet(resultSet.getString("street"));
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Load Address exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static Address loadAddress(final int addressID) {
        Address result = new Address();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT *
                    FROM addresses
                    WHERE addresses.id = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setInt(1, addressID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result.setID(resultSet.getInt("id"));
                result.setCountryCode(resultSet.getString("countryCode"));
                result.setCity(resultSet.getString("city"));
                result.setStreet(resultSet.getString("street"));
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Load Address exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static Customer loadCustomer(final String usernameC, final String passwordC) {
        Customer result = new Customer();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT *
                    FROM customers
                    WHERE customers.username = ? AND customers.password = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, usernameC);
            preparedStatement.setString(2, passwordC);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result.setID(resultSet.getInt("id"));
                result.setUsername(resultSet.getString("username"));
                result.setPassword(resultSet.getString("password"));
                result.setFirstName(resultSet.getString("firstName"));
                result.setLastName(resultSet.getString("lastName"));
                result.setAddressID(resultSet.getInt("addressID"));
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Load Customer exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static Product loadProduct(final String name) {
        Product result = new Product();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT *
                    FROM products
                    WHERE products.name = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result.setID(resultSet.getInt("id"));
                result.setName(resultSet.getString("name"));
                result.setPrice(resultSet.getDouble("price"));
                result.setQuantity(resultSet.getInt("quantity"));
                result.setWeight(resultSet.getDouble("weight"));
                result.setCategoryCode(resultSet.getString("categoryCode"));
                result.setColorCode(resultSet.getString("colorCode"));
                result.setInfo(resultSet.getString("info"));
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Load Customer exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    public static void editCustomer(final Customer customer) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    UPDATE customers
                    SET customers.username = ?, customers.password = ?, customers.customers.firstName = ?, customers.lastName = ?
                    WHERE customers.id = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getFirstName());
            preparedStatement.setString(4, customer.getLastName());
            preparedStatement.setInt(5, customer.getID());
            preparedStatement.executeUpdate();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Edit Customer exception: " + e);
        } finally {
            close();
        }
    }

    public static void editAddress(final Address address) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    UPDATE addresses
                    SET addresses.countryCode = ?, addresses.city = ?, addresses.street = ?
                    WHERE addresses.id = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, address.getCountryCode());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, address.getID());
            preparedStatement.executeUpdate();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Edit Address exception: " + e);
        } finally {
            close();
        }
    }

    public static void editProduct(final Product product) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    UPDATE products
                    SET products.name = ?, products.price = ?, products.quantity = ?, products.weight = ?, products.categoryCode, products.colorCode = ?, products.info = ?
                    WHERE products.id = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setDouble(4, product.getWeight());
            preparedStatement.setString(5, product.getCategoryCode());
            preparedStatement.setString(6, product.getColorCode());
            preparedStatement.setString(7, product.getInfo());
            preparedStatement.setInt(8, product.getID());

            preparedStatement.executeUpdate();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Edit Product exception: " + e);
        } finally {
            close();
        }
    }

    public static void filterProducts(final double lowPrice, final double highPrice,
                                      final int lowQuantity, final int highQuantity, final double lowWeight, final double highWeight,
                                      final String categoryCode, final String colorCode) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = _makeSQLforFilerProducts(lowPrice, highPrice, lowQuantity, highQuantity, lowWeight, highWeight,
                    categoryCode, colorCode);

            preparedStatement = connect.prepareStatement(mysql);

            _setParametersForFilerProducts(preparedStatement, lowPrice, highPrice, lowQuantity, highQuantity, lowWeight,
                    highWeight, categoryCode, colorCode);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("products.name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double weight = resultSet.getDouble("weight");
                String category = resultSet.getString("category.name");
                String color = resultSet.getString("color.name");
                String info = resultSet.getString("info");

                System.out.println("name: " + name);
                System.out.println("price: " + price);
                System.out.println("quantity: " + quantity);
                System.out.println("weight: " + weight);
                System.out.println("category: " + category);
                System.out.println("color: " + color);
                System.out.println("info: " + info);
                System.out.println();
            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Filter Product exception: " + e);
        } finally {
            close();
        }
    }

    private static String _makeSQLforFilerProducts(final double lowPrice, final double highPrice,
                                                   final int lowQuantity, final int highQuantity, final double lowWeight, final double highWeight,
                                                   final String categoryCode, final String colorCode) {
        boolean moreThanOne = false;

        String result = """
                SELECT products.name, products.price, products.quantity, products.weight, category.code, color.name, products.info
                FROM products
                LEFT JOIN category
                ON products.categoryCode = category.code
                LEFT JOIN color
                ON products.colorCode = color.code
                WHERE """;

        if (lowPrice != highPrice) {
            result += "product.price >= ? AND product.price <= ? ";
            moreThanOne = true;
        }

        if (lowQuantity != highQuantity) {
            if (moreThanOne) {
                result += "AND ";
            }
            moreThanOne = true;
            result += "product.quantity >= ? AND product.quantity <= ? ";
        }

        if (lowWeight != highWeight) {
            if (moreThanOne) {
                result += "AND ";
            }
            moreThanOne = true;
            result += "product.weight >= ? AND product.weight <= ? ";
        }

        if (!(categoryCode.equals("--"))) {
            if (moreThanOne) {
                result += "AND ";
            }
            moreThanOne = true;
            result += "product.categoryCode = ? ";
        }

        if (!(colorCode.equals("--"))) {
            if (moreThanOne) {
                result += "AND ";
            }
            result += "product.colorCode = ? ";
        }

        return result + ";";
    }

    private static void _setParametersForFilerProducts(final PreparedStatement prep,
                                                       final double lowPrice, final double highPrice, final int lowQuantity, final int highQuantity,
                                                       final double lowWeight, final double highWeight, final String categoryCode, final String colorCode)
            throws Exception {
        int position = 1;

        if (lowPrice != highPrice) {
            prep.setDouble(position, lowPrice);
            position++;
            prep.setDouble(position, highPrice);
            position++;
        }

        if (lowQuantity != highQuantity) {
            prep.setInt(position, lowQuantity);
            position++;
            prep.setInt(position, lowQuantity);
            position++;
        }

        if (lowWeight != highWeight) {
            prep.setDouble(position, lowWeight);
            position++;
            prep.setDouble(position, lowWeight);
            position++;
        }

        if (!(categoryCode.equals("--"))) {
            prep.setString(position, categoryCode);
            position++;
        }

        if (!(colorCode.equals("--"))) {
            prep.setString(position, colorCode);
        }
    }

    public static void searchProducts(final String search) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT products.name, products.price, products.quantity, products.weight, category.code, color.name, products.info
                    FROM products
                    LEFT JOIN category
                    ON products.categoryCode = category.code
                    LEFT JOIN color
                    ON products.colorCode = color.code
                    WHERE products.name LIKE '%?%' OR products.info LIKE '%?%';
                    """;

            preparedStatement = connect.prepareStatement(mysql);

            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("products.name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double weight = resultSet.getDouble("weight");
                String category = resultSet.getString("category.name");
                String color = resultSet.getString("color.name");
                String info = resultSet.getString("info");

                System.out.println("name: " + name);
                System.out.println("price: " + price);
                System.out.println("quantity: " + quantity);
                System.out.println("weight: " + weight);
                System.out.println("category: " + category);
                System.out.println("color: " + color);
                System.out.println("info: " + info);
                System.out.println();
            }
            System.out.println();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Search Product exception: " + e);
        } finally {
            close();
        }
    }

    public static void deleteCustomer(final Customer customer) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    DELETE FROM customers
                    WHERE customers.id = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setInt(1, customer.getID());
            preparedStatement.executeQuery();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Edit Address exception: " + e);
        } finally {
            close();
        }
    }

    public static void deleteProduct(final Product product) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    DELETE FROM products
                    WHERE product.id = ?;
                    """;
            preparedStatement = connect.prepareStatement(mysql);
            preparedStatement.setInt(1, product.getID());
            preparedStatement.executeQuery();

            connect.commit();
        } catch (Exception e) {
            System.out.println("Delete Product exception: " + e);
        } finally {
            close();
        }
    }

    public static int availableQuantity(final Product product) {
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, username, password);

            connect.setAutoCommit(false);

            statement = connect.createStatement();

            System.out.println("?");

            String mysql = """
                    SELECT SUM(orderedProducts.quantity)
                    FROM orderedProducts
                    WHERE orderedProducts.productID = ?;""";

            preparedStatement = connect.prepareStatement(mysql);

            preparedStatement.setInt(1, product.getID());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = product.getQuantity() - resultSet.getInt("SUM(orderedProducts.quantity)");
            }

            connect.commit();
        } catch (Exception e) {
            System.out.println("Filter Product exception: " + e);
        } finally {
            close();
        }

        return result;
    }

    private static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            System.out.println("Close exception: " + e);
        }
    }
}
