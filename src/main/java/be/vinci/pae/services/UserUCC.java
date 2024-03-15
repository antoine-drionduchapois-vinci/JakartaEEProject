package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public interface UserUCC {

  ObjectNode login(String email, String password);

  int getGlobalStats();

  List<UserDTO> getUsersAsJson();

  ObjectNode register(User user1);

  User createUserAndReturn(String name, String firstname, String email, String telephone,
      String password, String role);

  UserDTO getUsersByIdAsJson(int userId);
}
