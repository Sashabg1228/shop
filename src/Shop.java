import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private Customer activeUser; // -1 = guest
    private ArrayList<Product> basket;
    private ArrayList<Integer> quantities;

    public Shop() {
        this.activeUser = new Customer();
        this.basket = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    /*
     * * registerCustomer(Customer) - Метода трябва да генерира произволно ID и да
     * добави новия клиент към списъка с клиенти.
     */
    public void registerCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Enter firstName:");
        String firstName = scanner.nextLine();

        System.out.println("Enter lastName:");
        String lastName = scanner.nextLine();

        System.out.println("Customer address");
        MySQLdb.showCountries();
        System.out.println("Enter Country(from the list or error):");
        String country = scanner.nextLine();
        country = MySQLdb.checkCountry(country.toUpperCase());
        if (country.equals("--")) {
            System.out.println("I told you. ERROR.");
            scanner.close();
            return;
        }

        System.out.println("Enter city:");
        String city = scanner.nextLine();

        System.out.println("Enter street:");
        String street = scanner.nextLine();

        MySQLdb.uploadAddress(country, city, street);
        Address ad = MySQLdb.loadAddress(country, city, street);

        MySQLdb.uploadCustomer(username, password, firstName, lastName, ad.getID());

        scanner.close();
    }

    /*
     * login(username, password) - Метода търси в списъка с кленти дали има
     * съществуващ клиент с тези имена и парола и активният потребител става клента
     * с тези имена.
     */
    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        this.activeUser = MySQLdb.loadCustomer(username, password);

        scanner.close();
    }

    /*
     * logout() - Активният потребител се сменя на гост.
     */
    public void logout() {
        this.activeUser = new Customer();
    }

    /*
     * editCustomer() - ако активния потребител не е гост позволява да му редактираш
     * полетата, с изключение на IDто.
     */
    public void editCustomer() {
        if (this.activeUser.getID() == -1) {
            System.out.println("Cannot edit guest. ERROR.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to edit the username? Y/N");
        String choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new username:");
            this.activeUser.setUsername(scanner.nextLine());
        }

        System.out.println("Do you want to edit the password? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new password:");
            this.activeUser.setPassword(scanner.nextLine());
        }

        System.out.println("Do you want to edit the first name? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new first name:");
            this.activeUser.setFirstName(scanner.nextLine());
        }

        System.out.println("Do you want to edit the last name? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new last name:");
            this.activeUser.setLastName(scanner.nextLine());
        }

        System.out.println("Do you want to edit the address? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new last name:");
            addressEditor();
        }

        MySQLdb.editCustomer(this.activeUser);
        scanner.close();
    }

    private void addressEditor() {
        Address activeUserAddress = MySQLdb.loadAddress(this.activeUser.getAddressID());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to edit the city? Y/N");
        String choice = scanner.nextLine();

        if (choice.equals("Y") || choice.equals("y")) {
            MySQLdb.showCountries();
            System.out.println("Enter Country(from the list or error):");
            String country = scanner.nextLine();
            country = MySQLdb.checkCountry(country.toUpperCase());
            if (country.equals("--")) {
                System.out.println("I told you. ERROR.");
                scanner.close();
                return;
            }
            activeUserAddress.setCountryCode(country);
        }

        System.out.println("Do you want to edit the city? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter city:");
            activeUserAddress.setCity(scanner.nextLine());
        }

        System.out.println("Do you want to edit the street? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter street:");
            activeUserAddress.setStreet(scanner.nextLine());
        }

        MySQLdb.editAddress(activeUserAddress);
        scanner.close();
    }

    /*
     * deleteUser() - Изтрива активния потребител от списъка с клиенти и активния
     * потребител се сменя на гост.
     */
    public void deleteCustomer() {
        if (this.activeUser.getID() == -1) {
            System.out.println("Cannot delete guest. ERROR.");
            return;
        }

        MySQLdb.deleteCustomer(this.activeUser);
        this.activeUser = new Customer();
    }

    /*
     * addItem(Item, amount) - Метода трябва да генерира произволно ID и да добави
     * новия артикул към списъка с артикули с посочената наличност.
     * Ако бъде подаден Item обект с попълнено ID, то тогава количеството на
     * посочения артикул трябва да се увеличи.
     */
    public void addProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter name:");
        String name = scanner.nextLine();

        System.out.println("Enter price:");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Enter quantity:");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter weight:");
        double weight = Double.parseDouble(scanner.nextLine());

        System.out.println("Enter category(from the list or error):");
        String category = scanner.nextLine();
        category = MySQLdb.checkCategory(category.toUpperCase());
        if (category.equals("--")) {
            System.out.println("I told you. ERROR.");
            scanner.close();
            return;
        }

        System.out.println("Enter info:");
        String info = scanner.nextLine();

        MySQLdb.uploadProduct(name, price, quantity, weight, category, info);

        scanner.close();
    }

    private Product selectProduct() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Which product do you want to select?(name)");
        String name = scanner.nextLine();

        scanner.close();
        return MySQLdb.loadProduct(name);
    }

    /*
     * removeItem(Item, amount) - от наличностите трябва да бъде извадено посоченото
     * количество.
     */
    public void removeProduct(final Product product, final int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        MySQLdb.editProduct(product);
    }

    /*
     * editItem(Item) - редактира дадения артикул
     */
    public void editProduct() {
        Product product = selectProduct();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to edit the name? Y/N");
        String choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new name:");
            product.setName(scanner.nextLine());
        }

        System.out.println("Do you want to edit the price? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new price:");
            product.setPrice(Double.parseDouble(scanner.nextLine()));
        }

        System.out.println("Do you want to edit the quantity? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new quantity:");
            product.setQuantity(Integer.parseInt(scanner.nextLine()));
        }

        System.out.println("Do you want to edit the Weight? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new Weight:");
            product.setWeight(Double.parseDouble(scanner.nextLine()));
        }

        System.out.println("Do you want to edit the category? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            MySQLdb.showCategory();
            System.out.println("Enter new category(from the list or error):");
            String in = MySQLdb.checkCategory(scanner.nextLine().toUpperCase());
            if (in.equals("--")) {
                System.out.println("I told you. ERROR.");
            } else {
                product.setCategoryCode(in);
            }
        }

        System.out.println("Do you want to edit the info? Y/N");
        choice = scanner.nextLine();
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter new info:");
            product.setInfo(scanner.nextLine());
        }

        MySQLdb.editProduct(product);
        scanner.close();
    }

    /*
     * showProductList() - показва целия списък със продукти
     */
    public void showProductList() {
        MySQLdb.showProducts();
    }

    /*
     * filterItems() - търси продукти по зададените филтри и връща всички, отговарящи им.
     */
    public void filterProducts() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want to add price limits? Y/N");
        String choice = scanner.nextLine();
        double lowPrice = 0, highPrice = 0;
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter price limits(format 'number [enter] number'):");
            lowPrice = Double.parseDouble(scanner.nextLine());
            highPrice = Double.parseDouble(scanner.nextLine());
        }

        System.out.println("Do you want to add quantity limits? Y/N");
        choice = scanner.nextLine();
        int lowQuantity = 0, highQuantity = 0;
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter quantity limits(format 'number [enter] number'):");
            lowQuantity = Integer.parseInt(scanner.nextLine());
            highQuantity = Integer.parseInt(scanner.nextLine());
        }

        System.out.println("Do you want to add weight limits? Y/N");
        choice = scanner.nextLine();
        double lowWeight = 0, highWeight = 0;
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter weight limits(format 'number [enter] number'):");
            lowWeight = Double.parseDouble(scanner.nextLine());
            highWeight = Double.parseDouble(scanner.nextLine());
        }

        System.out.println("Do you want to add category limit? Y/N");
        choice = scanner.nextLine();
        String category = "--";
        if (choice.equals("Y") || choice.equals("y")) {
            MySQLdb.showCategory();
            System.out.println("Enter category(from the list or error):");
            category = scanner.nextLine();
            category = MySQLdb.checkCategory(category.toUpperCase());
        }

        MySQLdb.filterProducts(lowPrice, highPrice, lowQuantity, highQuantity, lowWeight, highWeight, category);
        scanner.close();
    }

    public void searchProducts() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Serach:");
        String search = scanner.nextLine();

        MySQLdb.searchProducts(search);
        scanner.close();
    }

    /*
     * deleteItem(Item) - изтрива дадения артикул от списъка с артикули
     */
    public void deleteProduct() {
        Product product = selectProduct();

        MySQLdb.deleteProduct(product);
    }

    /*
     * addItemToCart(ID, amount) - добавя артикула към активната кошница с
     * определеното количество.
     */
    public void addProductToCart() {
        Product product = selectProduct();

        Scanner scanner = new Scanner(System.in);
        System.out.println("What quantity of this product do you want to add to the basket?");
        int in = Integer.parseInt(scanner.nextLine());
        scanner.close();

        if (MySQLdb.availableQuantity(product) - in < 0) {
            System.out.println("Not enough quantity of this product. ERROR");
            return;
        }

        this.quantities.add(in);
        this.basket.add(product);
    }

    /*
     * checkoutCart() -
     * Ако са всички продукти от кошницата са налични създава поръчка в статус Open
     * и активния потребител.
     * Ако активния потребител е гост, тогава трябва да се логне.
     */
    public void checkoutCart() {
        if (this.activeUser.getID() == -1) {
            System.out.println("Guest cannot order. ERROR.");
            return;
        }
        if (!(MySQLdb.allowCountry(this.activeUser.getAddressID()))) {
            System.out.println("The country is not allowed. ERROR.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Phone number for the order:");
        String in = scanner.nextLine();
        scanner.close();

        for (int i = 0; i < basket.size(); i++) {
            MySQLdb.uploadOrder(this.activeUser.getID(), this.activeUser.getAddressID(), in, "O");
        }
    }
}

/*
 * checkOrder(ID) - показва поръчката със съответното ID.
 *
 * shipOrder(ID) - променя статус на поръчката на Shipped.
 * Ако страната до която трябва да бъде доставена поръчката не е в списъка на
 * държави до които доставя магазина => грешка и сменяме статус на поръчката на
 * Failed
 *
 * completeOrder(ID) - променя статус на поръчката на Completed.
 *
 * failOrder(ID) - променя статус на поръчката на Failed.
 *
 */