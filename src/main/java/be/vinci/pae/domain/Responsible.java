package be.vinci.pae.domain;

public interface Responsible {

  /**
   * Gets the enterprise ID associated with the responsible.
   *
   * @return The ID of the enterprise.
   */
  int getEnterprise();

  /**
   * Gets the unique identifier for the responsible.
   *
   * @return The responsible ID.
   */
  int getResponsibleId();

  /**
   * Gets the email address of the responsible.
   *
   * @return The email address.
   */
  String getEmail();

  /**
   * Gets the name of the responsible.
   *
   * @return The name.
   */
  String getName();

  /**
   * Gets the phone number of the responsible.
   *
   * @return The phone number.
   */
  String getPhone();

  /**
   * Gets the surname of the responsible.
   *
   * @return The surname.
   */
  String getSurname();
}
