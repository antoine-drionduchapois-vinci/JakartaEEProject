package be.vinci.pae.resources;

import be.vinci.pae.domain.UserDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JWT {

  ObjectNode createToken(UserDTO userDTO);
}
