package be.vinci.pae.domain;

/**
 * Interface representing an enterprise entity.
 */
public interface Enterprise extends EnterpriseDTO {

  /**
   * Blacklist the enterprise and give the reason for blacklisting.
   *
   * @param blacklistedReason the reason for blacklisting
   * @return true if the enterprise is not already blacklisted, otherwise false
   */
  boolean toBlacklist(String blacklistedReason);
}
