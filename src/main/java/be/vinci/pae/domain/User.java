package be.vinci.pae.domain;

public interface User {

  int getId();

  String getEmail();

  boolean checkPassword(String password);

  String hashPassword(String password);
}
