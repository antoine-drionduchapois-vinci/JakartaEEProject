package be.vinci.pae.domain;

/**
 * The Contact interface represents contact methods.
 */
public interface Contact extends ContactDTO {

  /**
   * set the contact as "pris".
   *
   * @param meetingPoint The meeting point to set.
   * @return True if the contact successfully changed to "pris" state.
   */
  boolean meet(String meetingPoint);

  /**
   * Sets the contact as refused with the specified refusal reason.
   *
   * @param refusalReason The reason for refusal.
   */
  void inidcateAsRefused(String refusalReason);

  /**
   * Sets the contact as unfollowed.
   */
  void unfollow();

}
