package be.vinci.pae.ucc;

import be.vinci.pae.domain.ContactDTO;
import java.util.List;

/**
 * The ContactUCC interface provides methods for managing contact-related
 * functionality.
 */
public interface ContactUCC {

  /**
   * Retrieves a list of contacts associated with the specified user ID.
   * 
   * @param userId The ID of the user whose contacts are to be retrieved.
   * @return A list of contacts associated with the specified user ID, or null if
   *         no contacts are
   *         found.
   */
  List<ContactDTO> getContacts(int userId);

  /**
   * Retrieves the contact information associated with the specified contact ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return An ObjectNode containing the contact information if found, or null if not found.
   */
  ContactDTO getContact(int contactId);

  /**
   * Initiates a contact between a user and an enterprise identified by their respective IDs.
   *
   * @param userId       The ID of the user initiating the contact.
   * @param enterpriseId The ID of the enterprise to initiate contact with.
   * @return An ObjectNode containing the newly created contact information if successful, or null
   * if the contact already exists.
   */
  ContactDTO initiateContact(int userId, int enterpriseId);


  ContactDTO initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAddress, String enterprisePhone, String enterpriseEmail);

  /**
   * Updates the contact status to "pris" and sets the meeting point for the specified contact.
   *
   * @param contactId    The ID of the contact to update.
   * @param meetingPoint The meeting point for the contact.
   * @return An ObjectNode containing the updated contact information.
   */
  ContactDTO meetEnterprise(int contactId, String meetingPoint);

  /**
   * Indicates that the contact associated with the specified ID has been refused with the provided
   * reason.
   *
   * @param contactId The ID of the contact to mark as refused.
   * @param reason    The reason for refusing the contact.
   * @return An ObjectNode containing the updated contact information.
   */
  ContactDTO indicateAsRefused(int contactId, String reason);

  /**
   * Marks the contact associated with the specified ID as "non_suivis".
   *
   * @param contactId The ID of the contact to unfollow.
   * @return An ObjectNode containing the updated contact information.
   */
  ContactDTO unfollow(int contactId);
}
