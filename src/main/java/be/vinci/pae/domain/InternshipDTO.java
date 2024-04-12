package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The InternshipDTO interface represents the internship details.
 */
@JsonDeserialize(as = InternshipImpl.class)
public interface InternshipDTO {

  /**
   * Gets the ID of the internship.
   *
   * @return The ID of the internship.
   */
  int getInternshipId();

  /**
   * Sets the ID of the internship.
   *
   * @param internshipId The new ID of the internship.
   */
  void setInternshipId(int internshipId);

  /**
   * Gets the ID of the responsible person for the internship.
   *
   * @return The ID of the responsible person.
   */
  int getSupervisor();

  /**
   * Gets the subject of the internship.
   *
   * @return The subject of the internship.
   */
  String getSubject();

  /**
   * Sets the subject of the internship.
   *
   * @param subject The new subject of the internship.
   */
  void setSubject(String subject);

  /**
   * Gets the ID of the enterprise associated with the internship.
   *
   * @return The ID of the associated enterprise.
   */
  int getEnterprise();

  /**
   * Sets the ID of the enterprise associated with the internship.
   *
   * @param enterprise The new ID of the associated enterprise.
   */
  void setEnterprise(int enterprise);

  /**
   * Gets the ID of the contact associated with the internship.
   *
   * @return The ID of the associated contact.
   */
  int getContact();

  /**
   * Sets the ID of the contact associated with the internship.
   *
   * @param contact The new ID of the associated contact.
   */
  void setContact(int contact);

  /**
   * Gets the ID of the user associated with the internship.
   *
   * @return The ID of the associated user.
   */
  int getUser();

  /**
   * Sets the ID of the user associated with the internship.
   *
   * @param user The new ID of the associated user.
   */
  void setUser(int user);

  /**
   * Gets the academic year of the internship.
   *
   * @return The academic year of the internship.
   */
  String getYear();

  /**
   * Sets the academic year of the internship.
   *
   * @param year The new academic year of the internship.
   */
  void setYear(String year);

  /**
   * Sets the ID of the supervisor responsible for the internship.
   *
   * @param supervisor The new ID of the supervisor responsible for the internship.
   */
  void setSupervisor(int supervisor);

  SupervisorDTO getSupervisorDTO();

  void setSupervisorDTO(SupervisorDTO supervisorDTO);

  ContactDTO getContactDTO();

  void setContactDTO(ContactDTO contactDTO);

  /**
   * Gets the version of the internship.
   *
   * @return The version of the internship.
   */
  int getVersion();

  /**
   * Sets the version of the internship.
   *
   * @param version The version of the internship to set.
   */
  void setVersion(int version);

  EnterpriseDTO getEnterpriseDTO();

  void setEnterpriseDTO(EnterpriseDTO enterpriseDTO);
}
