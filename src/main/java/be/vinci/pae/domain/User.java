package be.vinci.pae.domain;

public interface User {

  int getId();

  String getEmail();

  boolean checkPassword(String password);

  String hashPassword(String password);

  public enum Role {
    STUDENT("Student"), TEACHER("Teacher"), ADMIN("Admin");
    private String value;

    Role(String value) {
      this.value = value;
    }
  }
}
