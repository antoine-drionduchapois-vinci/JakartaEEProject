package be.vinci.pae.domain;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Implementation of the User interface.
 */
class UserImpl implements User {

  private int userId;
  private String email;
  private String password;

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
