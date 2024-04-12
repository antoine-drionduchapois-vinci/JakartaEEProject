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
   * @return a InternshipDTO object representing the user internship with the
   *         specified ID
   */
  InternshipDTO getUserInternship(int id);

  /**
   * Creates a new internship entry in the database.
   *
   * @param internship The internship object to be created.
   * @return The created internship object.
   */
  InternshipDTO create(InternshipDTO internship);
}
