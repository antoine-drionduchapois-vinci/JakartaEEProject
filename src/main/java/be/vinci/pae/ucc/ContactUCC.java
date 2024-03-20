package be.vinci.pae.ucc;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ContactUCC {

  ObjectNode getContact(int contactid);

  ObjectNode initiateContact(int userId, int enterpriseId);

  ObjectNode initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAddress, String enterpriseContact);

  ObjectNode meetEnterprise(int contactId, String meetingPoint);

  ObjectNode indicateAsRefused(int contactId, String reason);
}
