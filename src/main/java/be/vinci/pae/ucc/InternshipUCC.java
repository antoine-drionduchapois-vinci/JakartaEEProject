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

  InternshipDTO acceptInternship(InternshipDTO internship);
}
