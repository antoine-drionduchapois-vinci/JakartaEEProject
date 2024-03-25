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


  @Override
  public int getInternshipId() {
    return internshipId;
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
  public int getEnterprise() {
    return enterprise;
  }

  @Override
  public int getContact() {
    return contact;
  }

  @Override
  public int getUser() {
    return user;
  }

  @Override
  public String getYear() {
    return year;
  }


}
