public class Product {
  private int id;
  private String name;
  private double price;
  private int quantity;
  private double weight;
  private String categoryCode;
  private String colorCode;
  private String info;

  public Product() {
    this.id = -1;
  }

  public int getID() {
    return this.id;
  }

  public void setID(final int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public double getPrice() {
    return this.price;
  }

  public void setPrice(final double price) {
    this.price = price;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public void setQuantity(final int quantity) {
    this.quantity = quantity;
  }

  public double getWeight() {
    return this.weight;
  }

  public void setWeight(final double weight) {
    this.weight = weight;
  }

  public String getCategoryCode() {
    return this.categoryCode;
  }

  public void setCategoryCode(final String categoryCode) {
    this.categoryCode = categoryCode;
  }

  public String getColorCode() {
    return this.colorCode;
  }

  public void setColorCode(final String colorCode) {
    this.colorCode = colorCode;
  }

  public String getInfo() {
    return this.info;
  }

  public void setInfo(final String info) {
    this.info = info;
  }
}
