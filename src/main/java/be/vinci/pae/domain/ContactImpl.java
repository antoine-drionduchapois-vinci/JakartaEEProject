package be.vinci.pae.domain;

/**
 * Implementation of the Contact interface.
 */
public class ContactImpl implements Contact {

  private int contactId;
  private String meetingPoint;
  private String state;
  private String refusalReason;
  private String year;
  private int user;
  private int enterprise;
  private EnterpriseDTO enterpriseDTO;
  private int version;

  @Override
  public boolean meet(String meetingPoint) {
    if (!state.equals("initiated")) {
      return false;
    }
    this.meetingPoint = meetingPoint;
    state = "meet";
    return true;
  }

  @Override
  public boolean indicateAsRefused(String refusalReason) {
    if (!state.equals("initiated") && !state.equals("meet")) {
      return false;
    }
    this.refusalReason = refusalReason;
    state = "refused";
    return true;
  }

  @Override
  public boolean indicateAsSuspended() {
    if (!state.equals("initiated") && !state.equals("meet")) {
      return false;
    }
    state = "suspended";
    return true;
  }

  @Override
  public boolean unfollow() {
    if (!state.equals("initiated") && !state.equals("meet")) {
      return false;
    }
    state = "unfollowed";
    return true;
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
  public EnterpriseDTO getEnterpriseDTO() {
    return enterpriseDTO;
  }

  @Override
  public void setEnterpriseDTO(EnterpriseDTO enterpriseDTO) {
    this.enterpriseDTO = enterpriseDTO;
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
    return "ContactImpl{"
        + "contactId=" + contactId
        + ", meetingPoint='" + meetingPoint + '\''
        + ", state='" + state + '\''
        + ", refusalReason='" + refusalReason + '\''
        + ", year='" + year + '\''
        + ", user=" + user
        + ", enterprise=" + enterprise
        + ", enterpriseDTO=" + enterpriseDTO
        + ", version=" + version
        + '}';
  }
}
