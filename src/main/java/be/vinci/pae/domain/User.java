package be.vinci.pae.domain;

public interface User {

  boolean checkPassword(String password);

  String hashPassword(String password);

  int getUserId();

  void setUserId(int userId);

  String getName();

  void setName(String name);

  String getSurname();

  void setSurname(String surname);

  String getEmail();

  void setEmail(String email);

  String getPhone();

  void setPhone(String phone);

  String getPassword();

  void setPassword(String password);

  String getYear();

  void setYear(String year);

  Role getRole();

  void setRole(Role role);

  @Override
  String toString();

  public enum Role {
    STUDENT,
    TEACHER,
    ADMIN
  }
}
