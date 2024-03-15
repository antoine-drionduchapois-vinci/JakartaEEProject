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
   * Gets the ID of the enterprise associated with the contact.
   *
   * @return The ID of the enterprise associated with the contact.
   */
  int getEntreprise();

  /**
   * Gets the ID of the user associated with the contact.
   *
   * @return The ID of the user associated with the contact.
   */
  int getUser();

  /**
   * Gets the description of the contact.
   *
   * @return The description of the contact.
   */
  String getDescription();

  /**
   * Gets the reason for refusal of the contact.
   *
   * @return The reason for refusal of the contact.
   */
  String getReasonRefusal();

  /**
   * Gets the state of the contact.
   *
   * @return The state of the contact.
   */
  String getState();

  /**
   * Gets the year of the contact.
   *
   * @return The year of the contact.
   */
  String getYear();
}
