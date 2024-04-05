package be.vinci.pae.dao;

import be.vinci.pae.domain.InternshipDTO;

/**
 * Represents a Data Access Object (DAO) for internship entities.
 */
public interface InternshipDAO {

  /**
   * Retrieves a user internshp by their ID.
   *
   * @param id the ID of the user
   * @return a InternshipDTO object representing the user internship with the specified ID
   */
  InternshipDTO getUserInternship(int id);

  InternshipDTO create(InternshipDTO internship);
}
