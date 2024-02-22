package be.vinci.pae.domain;

import org.mindrot.jbcrypt.BCrypt;

class UserImpl implements User {

  private int userId;
  private String name;
  private String surname;
  private String email;
  private String phone;
  private String password;
  private String year;
  private Role role;

  @Override
  public int getId() {
    return userId;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
}
