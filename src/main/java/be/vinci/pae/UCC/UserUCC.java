package be.vinci.pae.UCC;

import be.vinci.pae.domain.UserDTO;
import java.util.List;

public interface UserUCC {

  int getGlobalStats();

  List<UserDTO> getUsersAsJson();

  UserDTO getUsersByIdAsJson(int userId);
}
