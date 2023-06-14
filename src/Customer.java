public class Customer {
  private int id;
  private String username; // max len 25
  private String password; // max len 25
  private String firstName; // max len 25
  private String lastName; // max len 25
  private int addressID;

  public Customer() {
    this.id = -1;
  }

  public int getID() {
    return this.id;
  }

  public void setID(final int id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public int getAddressID() {
    return this.addressID;
  }

  public void setAddressID(final int addressID) {
    this.addressID = addressID;
  }
}
