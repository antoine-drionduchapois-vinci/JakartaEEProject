package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface AuthUCC {

  ObjectNode login(String email, String password);

  ObjectNode register(User user1);

  User createUserAndReturn(String name, String firstname, String email, String telephone,
      String password, String role);
}
