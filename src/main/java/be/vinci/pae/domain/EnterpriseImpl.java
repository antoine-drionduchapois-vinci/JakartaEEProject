package be.vinci.pae.domain;

/**
 * Implementation of the Enterprise interface.
 */
public class EnterpriseImpl implements Enterprise {

  private int enterpriseId;
  private String name;
  private String label;
  private String address;
  private String contactInfos;
  private boolean isBlacklisted;
  private String blacklistedReason;

  @Override
  public int getEnterpriseId() {
    return enterpriseId;
  }

  @Override
  public void setEnterpriseId(int enterpriseId) {
    this.enterpriseId = enterpriseId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String getAddress() {
    return address;
  }

  @Override
  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String getContactInfos() {
    return contactInfos;
  }

  @Override
  public void setContactInfos(String contactInfos) {
    this.contactInfos = contactInfos;
  }

  @Override
  public boolean isBlacklisted() {
    return isBlacklisted;
  }

  @Override
  public void setBlacklisted(boolean blacklisted) {
    isBlacklisted = blacklisted;
  }

  @Override
  public String getBlacklistedReason() {
    return blacklistedReason;
  }

  @Override
  public void setBlacklistedReason(String blacklistedReason) {
    this.blacklistedReason = blacklistedReason;
  }

  @Override
  public String toString() {
    return "EnterpriseImpl{"
        + "enterpriseId=" + enterpriseId
        + ", name='" + name + '\''
        + ", label='" + label + '\''
        + ", address='" + address + '\''
        + ", contact='" + contactInfos + '\''
        + ", isBlacklisted=" + isBlacklisted
        + ", blacklistedReason='" + blacklistedReason + '\''
        + '}';
  }
}
