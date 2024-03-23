package be.vinci.pae.domain;

public class InternshipImpl implements Internship {

  private int internshipId;
  private String subject;
  private String year;
  private int user;
  private int enterprise;
  private int responsible;
  private int contact;

  private Internship IntenshipDTO;


  @Override
  public int getInternshipId() {
    return internshipId;
  }

  @Override
  public int getResponsible() {
    return responsible;
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

  @Override
  public void setInternshipDTO(Internship InternshipDTO) {
    this.IntenshipDTO = InternshipDTO;
  }
}
