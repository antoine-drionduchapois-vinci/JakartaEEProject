package be.vinci.pae.domain;

import be.vinci.pae.views.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.mindrot.jbcrypt.BCrypt;

@JsonInclude(JsonInclude.Include.NON_NULL) // ignore all null fields in order to avoid sending props not linked to a JSON view
class UserImpl implements User {

  @JsonView(Views.Public.class)
  private int userId;
  @JsonView(Views.Public.class)
  private String name;
  @JsonView(Views.Public.class)
  private String surname;
  @JsonView(Views.Public.class)
  private String email;
  @JsonView(Views.Public.class)
  private String phone;
  @JsonView(Views.Internal.class)
  private String password;
  @JsonView(Views.Internal.class)
  private String year;
  @JsonView(Views.Public.class)
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
