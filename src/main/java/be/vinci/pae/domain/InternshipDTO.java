package be.vinci.pae.domain;

/**
 * The InternshipDTO interface represents the internship details.
 */
public interface InternshipDTO {

  /**
   * Gets the ID of the internship.
   *
   * @return The ID of the internship.
   */
  int getInternshipId();

  /**
   * Gets the ID of the responsible person for the internship.
   *
   * @return The ID of the responsible person.
   */
  int getResponsible();

  /**
   * Gets the subject of the internship.
   *
   * @return The subject of the internship.
   */
  String getSubject();

  /**
   * Gets the ID of the enterprise associated with the internship.
   *
   * @return The ID of the associated enterprise.
   */
  int getEnterprise();

  /**
   * Gets the ID of the contact associated with the internship.
   *
   * @return The ID of the associated contact.
   */
  int getContact();

  /**
   * Gets the ID of the user associated with the internship.
   *
   * @return The ID of the associated user.
   */
  int getUser();

  /**
   * Gets the academic year of the internship.
   *
   * @return The academic year of the internship.
   */
  String getYear();

  /**
   * Sets the InternshipDTO object.
   *
   * @param internshipDTO The InternshipDTO object to set.
   */
  void setInternshipDTO(InternshipDTO internshipDTO);
}
