package be.vinci.pae.domain;

public interface Contact {

  boolean meet(String meetingPoint);

  void inidcateAsRefused(String refusalReason);

  void unfollow();

  int getContactId();

  void setContactId(int contactId);

  String getMeetingPoint();

  void setMeetingPoint(String meetingPoint);

  String getState();

  void setState(String state);

  String getRefusalReason();

  void setRefusalReason(String refusalReason);

  String getYear();

  void setYear(String year);

  int getUser();

  void setUser(int user);

  int getEnterprise();

  void setEnterprise(int enterprise);

  Enterprise getEnterpriseDTO();

  void setEnterpriseDTO(Enterprise enterpriseDTO);
}
