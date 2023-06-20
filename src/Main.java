import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        MySQLdb.restartDataBase();

        Shop shop = new Shop();
        shop.login();
        // shop.showProductList();
        // shop.filterProducts();       // price 100 & 300
        // shop.searchProducts();       // red
        shop.addProductToCart();        // red pen
        shop.checkoutCart();            // why doesn't 'NOT DETERMINISTIC' work
        shop.showTurnover(new Date(2023, 5, 10), new Date(2023, 6, 28));
    }
}