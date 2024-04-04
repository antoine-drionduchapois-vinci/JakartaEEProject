package be.vinci.pae.domain;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Implementation of the Enterprise interface.
 */
public class UserImpl implements User {

  private int userId;
  private String name;
  private String surname;
  private String email;
  private String phone;

  private String password;
  private String year;
  private Role role;
  private int version;


  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return this.password = BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Override
  public int getUserId() {
    return userId;
  }

  @Override
  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getSurname() {
    return surname;
  }

  @Override
  public void setSurname(String surname) {
    this.surname = surname;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getYear() {
    return year;
  }

  @Override
  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public Role getRole() {
    return role;
  }

  @Override
  public void setRole(Role role) {
    this.role = role;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "UserImpl{"
        + "userId=" + userId
        + ", name='" + name + '\''
        + ", surname='" + surname + '\''
        + ", email='" + email + '\''
        + ", phone='" + phone + '\''
        + ", password='" + password + '\''
        + ", year='" + year + '\''
        + ", role=" + role
        + ", version=" + version
        + '}';
  }
}