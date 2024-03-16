package be.vinci.pae.ucc;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ContactUCC {

  ObjectNode initiateContact(int userId, int enterpriseId);

  ObjectNode initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAdress, String enterpriseContact);
}
