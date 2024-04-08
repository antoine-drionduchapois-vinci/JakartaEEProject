package be.vinci.pae.domain;

/**
 * Implementation of the Internship interface.
 */
public class InternshipImpl implements Internship {

  private int internshipId;
  private String subject;
  private String year;
  private int user;
  private int enterprise;
  private int supervisor;
  private int contact;
  private int version;


  @Override
  public int getInternshipId() {
    return internshipId;
  }

  @Override
  public void setInternshipId(int internshipId) {
    this.internshipId = internshipId;
  }

  @Override
  public int getResponsible() {
    return supervisor;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public void setSubject(String subject) {
    this.subject = subject;
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
  public int getContact() {
    return contact;
  }

  @Override
  public void setContact(int contact) {
    this.contact = contact;
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
  public String getYear() {
    return year;
  }

  @Override
  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public void setSupervisor(int supervisor) {
    this.supervisor = supervisor;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }
}
