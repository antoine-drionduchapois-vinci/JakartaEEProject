package be.vinci.pae.domain;

/**
 * Interface representing an Contact entity.
 */
public interface ContactDTO {

  /**
   * Gets the ID of the contact.
   *
   * @return The ID of the contact.
   */
  int getContactId();

  /**
   * Sets the ID of the user associated with the contact.
   *
   * @param user The ID of the user associated with the contact.
   */
  void setUser(int user);

  /**
   * Gets the ID of the enterprise associated with the contact.
   *
   * @return The ID of the enterprise associated with the contact.
   */
  int getEntreprise();

  /**
   * Sets the year of the contact.
   *
   * @param year The year of the contact.
   */
  void setYear(String year);

  /**
   * Gets the ID of the user associated with the contact.
   *
   * @return The ID of the user associated with the contact.
   */
  int getUser();

  /**
   * Sets the ID of the contact.
   *
   * @param contactId The ID of the contact.
   */
  void setContactId(int contactId);

  /**
   * Gets the description of the contact.
   *
   * @return The description of the contact.
   */
  String getDescription();

  /**
   * Sets the state of the contact.
   *
   * @param state The state of the contact.
   */
  void setState(String state);

  /**
   * Gets the reason for refusal of the contact.
   *
   * @return The reason for refusal of the contact.
   */
  String getReasonRefusal();

  /**
   * Sets the description of the contact.
   *
   * @param description The description of the contact.
   */
  void setDescription(String description);

  /**
   * Gets the state of the contact.
   *
   * @return The state of the contact.
   */
  String getState();

  /**
   * Sets the reason for refusal of the contact.
   *
   * @param reasonRefusal The reason for refusal of the contact.
   */
  void setReasonRefusal(String reasonRefusal);

  /**
   * Gets the year of the contact.
   *
   * @return The year of the contact.
   */
  String getYear();

  /**
   * Sets the ID of the enterprise associated with the contact.
   *
   * @param entreprise The ID of the enterprise associated with the contact.
   */
  void setEntreprise(int entreprise);
}
