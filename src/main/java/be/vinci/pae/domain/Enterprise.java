package be.vinci.pae.domain;

public interface Enterprise {

  int getEnterpriseId();

  void setEnterpriseId(int enterpriseId);

  String getName();

  void setName(String name);

  String getLabel();

  void setLabel(String label);

  String getAddress();

  void setAddress(String address);

  String getContactInfos();

  void setContactInfos(String contactInfos);

  boolean isBlacklisted();

  void setBlacklisted(boolean blacklisted);

  String getBlacklistedReason();

  void setBlacklistedReason(String blacklistedReason);
}
