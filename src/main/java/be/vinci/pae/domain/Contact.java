package be.vinci.pae.domain;

/**
 * The Contact interface represents a contact between a user and an enterprise.
 * It extends the ContactDTO interface and adds methods for interacting with the contact.
 */
public interface Contact extends ContactDTO {

  /**
   * Marks the contact as having a meeting at the specified meeting point.
   *
   * @param meetingPoint The meeting point where the contact took place.
   * @return true if the operation is successful, false otherwise.
   */
  boolean meet(String meetingPoint);

  /**
   * Indicates that the contact has been refused with the given reason.
   *
   * @param refusalReason The reason for refusing the contact.
   * @return true if the operation is successful, false otherwise.
   */
  boolean indicateAsRefused(String refusalReason);

  /**
   * Removes the contact from the user's list of contacts.
   *
   * @return true if the operation is successful, false otherwise.
   */
  boolean unfollow();


}
