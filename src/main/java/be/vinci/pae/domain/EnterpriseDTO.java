package be.vinci.pae.domain;

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
   * Gets the contact information of the enterprise.
   *
   * @return The contact information of the enterprise.
   */
  String getContactInfos();

  /**
   * Sets the contact information of the enterprise.
   *
   * @param contactInfos The contact information of the enterprise to set.
   */
  void setContactInfos(String contactInfos);

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
}
