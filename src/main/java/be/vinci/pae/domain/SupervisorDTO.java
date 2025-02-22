package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The SupervisorDTO interface represents supervisor informations.
 */
@JsonDeserialize(as = SupervisorImpl.class)
public interface SupervisorDTO {

  /**
   * Gets the name of the supervisor.
   *
   * @return The name of the supervisor.
   */
  String getName();

  /**
   * Sets the name of the supervisor.
   *
   * @param name The name of the supervisor to set.
   */
  void setName(String name);

  /**
   * Gets the surname of the supervisor.
   *
   * @return The surname of the supervisor.
   */
  String getSurname();

  /**
   * Sets the surname of the supervisor.
   *
   * @param surname The surname of the supervisor to set.
   */
  void setSurname(String surname);

  /**
   * Gets the phone number of the supervisor.
   *
   * @return The phone number of the supervisor.
   */
  String getPhone();

  /**
   * Sets the phone number of the supervisor.
   *
   * @param phone The phone number of the supervisor to set.
   */
  void setPhone(String phone);

  /**
   * Gets the email address of the supervisor.
   *
   * @return The email address of the supervisor.
   */
  String getEmail();

  /**
   * Sets the email address of the supervisor.
   *
   * @param email The email address of the supervisor to set.
   */
  void setEmail(String email);

  /**
   * Gets the ID of the enterprise associated with the supervisor.
   *
   * @return The ID of the enterprise associated with the supervisor.
   */
  int getEnterprise();

  /**
   * Sets the ID of the enterprise associated with the supervisor.
   *
   * @param enterprise The ID of the enterprise associated with the supervisor to
   *                   set.
   */
  void setEnterprise(int enterprise);

  /**
   * Gets the enterprise DTO associated with the supervisor.
   *
   * @return The enterprise DTO associated with the supervisor.
   */
  Enterprise getEnterpriseDTO();

  /**
   * Sets the enterprise DTO associated with the supervisor.
   *
   * @param enterpriseDTO The enterprise DTO associated with the supervisor to
   *                      set.
   */
  void setEnterpriseDTO(Enterprise enterpriseDTO);

  /**
   * Gets the supervisorId of the supervisor.
   *
   * @return The supervisorId of the supervisor.
   */
  int getSupervisorId();

  /**
   * Sets the supervisorId associated with the supervisor.
   *
   * @param supervisorId The enterprise supervisorId associated with the
   *                     supervisor to set.
   */
  void setSupervisorId(int supervisorId);

  /**
   * Gets the version of the supervisor.
   *
   * @return The version of the supervisor.
   */
  int getVersion();

  /**
   * Sets the version of the supervisor.
   *
   * @param version The version of the supervisor to set.
   */
  void setVersion(int version);
}
