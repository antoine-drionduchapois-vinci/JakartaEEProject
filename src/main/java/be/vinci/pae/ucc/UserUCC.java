package be.vinci.pae.ucc;

import be.vinci.pae.domain.UserDTO;
import java.util.List;
/**
 * Interface representing userUCC.
 */

public interface UserUCC {

  int getGlobalStats();

  List<UserDTO> getUsersAsJson();

  UserDTO getUsersByIdAsJson(int userId);
}
