package be.vinci.pae.domain;

/**
 * Implementation of the Enterprise interface.
 */
public class EnterpriseImpl implements Enterprise {

  private int enterpriseId;
  private String name;
  private String label;
  private String address;
  private String phone;
  private String email;
  private boolean isBlacklisted;
  private String blacklistedReason;
  private int version;

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
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String contactInfos) {
    this.phone = contactInfos;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
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
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "EnterpriseImpl{" +
        "enterpriseId=" + enterpriseId +
        ", name='" + name + '\'' +
        ", label='" + label + '\'' +
        ", address='" + address + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", isBlacklisted=" + isBlacklisted +
        ", blacklistedReason='" + blacklistedReason + '\'' +
        ", numVersion=" + version +
        '}';
  }
}
