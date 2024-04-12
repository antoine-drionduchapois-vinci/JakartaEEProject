package be.vinci.pae.ucc;

import be.vinci.pae.domain.InternshipDTO;

/**
 * The InternshipUCC interface provides methods for managing internship-related
 * functionality.
 */
public interface InternshipUCC {

  /**
   * Retrieves the internship associated with the specified user ID.
   *
   * @param userId The ID of the user.
   * @return An InternshipDTO object representing the
   *         internship associated with the specified user ID
   */
  InternshipDTO getUserInternship(int userId);

  /**
   * Accepts the internship and performs necessary validations and updates.
   *
   * @param internship The InternshipDTO object representing the internship to
   *                   accept.
   * @return The updated InternshipDTO object after accepting the internship.
   */
  InternshipDTO acceptInternship(InternshipDTO internship);

  /**
   * Modifies the subject of an existing internship associated with the provided
   * user ID.
   *
   * @param userId  The ID of the user whose internship subject is to be modified.
   * @param subject The new subject for the internship.
   * @return The updated InternshipDTO object with the modified subject.
   */
  InternshipDTO modifySubject(int userId, String subject);
}
