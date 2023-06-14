public class Address {
  private int id;
  private String countryCode;
  private String city;
  private String street;

  public Address() {
    this.id = -1;
  }

  public Address(final int id, final String countryCode, final String city, final String street) {
    this.id = id;
    this.countryCode = countryCode;
    this.city = city;
    this.street = street;
  }

  public int getID() {
    return this.id;
  }

  public void setID(final int id) {
    this.id = id;
  }

  public String getCountryCode() {
    return this.countryCode;
  }

  public void setCountryCode(final String countryCode) {
    this.countryCode = countryCode;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public String getStreet() {
    return this.street;
  }

  public void setStreet(final String street) {
    this.street = street;
  }
}
