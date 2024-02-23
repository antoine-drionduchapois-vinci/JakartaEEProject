package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UserDataService {

  User getOne(String email);

  ObjectNode login(String email, String password);
}
