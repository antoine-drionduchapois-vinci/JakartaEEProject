package be.vinci.pae.domain;

/**
 * The EnterpriseDTO interface represents enterprise informations.
 */
public interface EnterpriseDTO {

  /**
   * Gets the ID of the enterprise.
   *
   * @return The ID of the enterprise.
   */
  int getEnterpriseId();

  /**
   * Sets the ID of the enterprise.
   *
   * @param enterpriseId The ID of the enterprise to set.
   */
  void setEnterpriseId(int enterpriseId);

  /**
   * Gets the name of the enterprise.
   *
   * @return The name of the enterprise.
   */
  String getName();

  /**
   * Sets the name of the enterprise.
   *
   * @param name The name of the enterprise to set.
   */
  void setName(String name);

  /**
   * Gets the label of the enterprise.
   *
   * @return The label of the enterprise.
   */
  String getLabel();

  /**
   * Sets the label of the enterprise.
   *
   * @param label The label of the enterprise to set.
   */
  void setLabel(String label);

  /**
   * Gets the address of the enterprise.
   *
   * @return The address of the enterprise.
   */
  String getAddress();

  /**
   * Sets the address of the enterprise.
   *
   * @param address The address of the enterprise to set.
   */
  void setAddress(String address);

  /**
   * Gets the phone number of the enterprise.
   *
   * @return The phone number of the enterprise.
   */
  String getPhone();

  /**
   * Sets the phone number of the enterprise.
   *
   * @param phone The phone number of the enterprise to set.
   */
  void setPhone(String phone);

  /**
   * Gets the email of the enterprise.
   *
   * @return The email of the enterprise.
   */
  String getEmail();

  /**
   * Sets the email of the enterprise.
   *
   * @param email The email of the enterprise to set.
   */
  void setEmail(String email);

  /**
   * Checks if the enterprise is blacklisted.
   *
   * @return True if the enterprise is blacklisted, false otherwise.
   */
  boolean isBlacklisted();

  /**
   * Sets the blacklisted status of the enterprise.
   *
   * @param blacklisted True to mark the enterprise as blacklisted, false otherwise.
   */
  void setBlacklisted(boolean blacklisted);

  /**
   * Gets the reason for the enterprise being blacklisted.
   *
   * @return The reason for the enterprise being blacklisted.
   */
  String getBlacklistedReason();

  /**
   * Sets the reason for the enterprise being blacklisted.
   *
   * @param blacklistedReason The reason for the enterprise being blacklisted to set.
   */
  void setBlacklistedReason(String blacklistedReason);

  /**
   * Gets the version of the enterprise.
   *
   * @return The version of the enterprise.
   */
  int getVersion();

  /**
   * Sets the version of the enterprise.
   *
   * @param version The version of the user to enterprise.
   */
  void setVersion(int version);
}
