package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The Contact interface represents contact informations.
 */
@JsonDeserialize(as = ContactImpl.class)
public interface ContactDTO {

  /**
   * Gets the ID of the contact.
   *
   * @return The ID of the contact.
   */
  int getContactId();

  /**
   * Sets the ID of the contact.
   *
   * @param contactId The ID of the contact to set.
   */
  void setContactId(int contactId);

  /**
   * Gets the meeting point for the contact.
   *
   * @return The meeting point for the contact.
   */
  String getMeetingPoint();

  /**
   * Sets the meeting point for the contact.
   *
   * @param meetingPoint The meeting point to set.
   */
  void setMeetingPoint(String meetingPoint);

  /**
   * Gets the state of the contact.
   *
   * @return The state of the contact.
   */
  String getState();

  /**
   * Sets the state of the contact.
   *
   * @param state The state to set for the contact.
   */
  void setState(String state);

  /**
   * Gets the reason for refusal of the contact.
   *
   * @return The reason for refusal of the contact.
   */
  String getRefusalReason();

  /**
   * Sets the reason for refusal of the contact.
   *
   * @param refusalReason The reason for refusal to set.
   */
  void setRefusalReason(String refusalReason);

  /**
   * Gets the year of the contact.
   *
   * @return The year of the contact.
   */
  String getYear();

  /**
   * Sets the year of the contact.
   *
   * @param year The year to set for the contact.
   */
  void setYear(String year);

  /**
   * Gets the ID of the user associated with the contact.
   *
   * @return The ID of the user associated with the contact.
   */
  int getUser();

  /**
   * Sets the ID of the user associated with the contact.
   *
   * @param user The ID of the user to set for the contact.
   */
  void setUser(int user);

  /**
   * Gets the ID of the enterprise associated with the contact.
   *
   * @return The ID of the enterprise associated with the contact.
   */
  int getEnterprise();

  /**
   * Sets the ID of the enterprise associated with the contact.
   *
   * @param enterprise The ID of the enterprise to set for the contact.
   */
  void setEnterprise(int enterprise);

  /**
   * Gets the Enterprise object associated with the contact.
   *
   * @return The Enterprise object associated with the contact.
   */
  EnterpriseDTO getEnterpriseDTO();

  /**
   * Sets the Enterprise object associated with the contact.
   *
   * @param enterpriseDTO The Enterprise object to set for the contact.
   */
  void setEnterpriseDTO(EnterpriseDTO enterpriseDTO);

  /**
   * Gets the version of the contact.
   *
   * @return The version of the contact.
   */
  int getVersion();

  /**
   * Sets the version of the contact.
   *
   * @param version The version of the user to contact.
   */
  void setVersion(int version);

  /**
   * Gets the user object associated with the contact.
   *
   * @return The user object associated with the contact.
   */
  UserDTO getUserDTO();

  /**
   * Sets the user object associated with the contact.
   *
   * @param userDTO The Enterprise object to set for the contact.
   */
  void setUserDTO(UserDTO userDTO);
}
