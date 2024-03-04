package be.vinci.pae.api;

import be.vinci.pae.domain.UserDTO;

public interface UserDAO {

  UserDTO getOneByEmail(String email);

  UserDTO getOneByID(int id);
}
