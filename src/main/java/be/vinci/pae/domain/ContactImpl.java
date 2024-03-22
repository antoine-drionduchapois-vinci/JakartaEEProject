package be.vinci.pae.domain;

public class ContactImpl implements Contact {
  private int contactId;
  private String meetingPoint;
  private String state;
  private String refusalReason;
  private String year;
  private int user;
  private int enterprise;
  private Enterprise enterpriseDTO;

  @Override
  public boolean meet(String meetingPoint) {
    if (!state.equals("initié")) {
      return false;
    }
    this.meetingPoint = meetingPoint;
    state = "pris";
    return true;
  }

  @Override
  public void inidcateAsRefused(String refusalReason) {
    if (state.equals("refusé")) {
      return; // TODO: handle no content
    }
    if (!state.equals("initié") && !state.equals("pris")) {
      return; // TODO: handle forbidden
    }
    this.refusalReason = refusalReason;
    state = "refusé";
  }

  @Override
  public void unfollow() {
    if (state.equals("non_suivis")) {
      return; // TODO: handle no content
    }
    if (!state.equals("initié") && !state.equals("pris")) {
      return; // TODO: handle forbidden
    }
    state = "non_suivis";
  }

  @Override
  public int getContactId() {
    return contactId;
  }

  @Override
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }

  @Override
  public String getMeetingPoint() {
    return meetingPoint;
  }

  @Override
  public void setMeetingPoint(String meetingPoint) {
    this.meetingPoint = meetingPoint;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String getRefusalReason() {
    return refusalReason;
  }

  @Override
  public void setRefusalReason(String refusalReason) {
    this.refusalReason = refusalReason;
  }

  @Override
  public String getYear() {
    return year;
  }

  @Override
  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public int getUser() {
    return user;
  }

  @Override
  public void setUser(int user) {
    this.user = user;
  }

  @Override
  public int getEnterprise() {
    return enterprise;
  }

  @Override
  public void setEnterprise(int enterprise) {
    this.enterprise = enterprise;
  }

  @Override
  public Enterprise getEnterpriseDTO() {
    return enterpriseDTO;
  }

  @Override
  public void setEnterpriseDTO(Enterprise enterpriseDTO) {
    this.enterpriseDTO = enterpriseDTO;
  }

  @Override
  public String toString() {
    return "ContactImpl{" +
        "contactId=" + contactId +
        ", meetingPoint='" + meetingPoint + '\'' +
        ", state='" + state + '\'' +
        ", refusalReason='" + refusalReason + '\'' +
        ", year='" + year + '\'' +
        ", user=" + user +
        ", enterprise=" + enterprise +
        ", enterpriseDTO=" + enterpriseDTO +
        '}';
  }
}
