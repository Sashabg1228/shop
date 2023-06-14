public class Main {
    public static void main(String[] args) {
        MySQLdb.restartDataBase();

        Shop shop = new Shop();
        shop.showProductList();
/*        shop.filterProducts();
        shop.searchProducts();
        shop.addProductToCart();
*/
    }
}