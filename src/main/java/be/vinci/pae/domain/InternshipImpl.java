package be.vinci.pae.domain;

import java.time.LocalDate;

/**
 * Implementation of the Internship interface.
 */
public class InternshipImpl implements Internship {

  private int internshipId;
  private String subject;
  private String year;
  private int user;
  private int enterprise;
  private EnterpriseDTO enterpriseDTO;
  private int supervisor;
  private SupervisorDTO supervisorDTO;
  private int contact;
  private ContactDTO contactDTO;
  private int version;

  @Override
  public void accept() {
    year = getCurrentYearString();
  }

  @Override
  public int getInternshipId() {
    return internshipId;
  }

  @Override
  public void setInternshipId(int internshipId) {
    this.internshipId = internshipId;
  }

  @Override
  public int getSupervisor() {
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
  public SupervisorDTO getSupervisorDTO() {
    return supervisorDTO;
  }

  @Override
  public void setSupervisorDTO(SupervisorDTO supervisorDTO) {
    this.supervisorDTO = supervisorDTO;
  }

  @Override
  public ContactDTO getContactDTO() {
    return contactDTO;
  }

  @Override
  public void setContactDTO(ContactDTO contactDTO) {
    this.contactDTO = contactDTO;
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
  public EnterpriseDTO getEnterpriseDTO() {
    return enterpriseDTO;
  }

  @Override
  public void setEnterpriseDTO(EnterpriseDTO enterpriseDTO) {
    this.enterpriseDTO = enterpriseDTO;
  }

  @Override
  public String toString() {
    return "InternshipImpl{"
        + "internshipId=" + internshipId
        + ", subject='" + subject + '\''
        + ", year='" + year + '\''
        + ", user=" + user
        + ", enterprise=" + enterprise
        + ", enterpriseDTO=" + enterpriseDTO
        + ", supervisor=" + supervisor
        + ", supervisorDTO=" + supervisorDTO
        + ", contact=" + contact
        + ", contactDTO=" + contactDTO
        + ", version=" + version
        + '}';
  }

  private String getCurrentYearString() {
    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = LocalDate.of(currentDate.getYear() - 1, 9, 1);
    LocalDate endDate = LocalDate.of(currentDate.getYear(), 9, 1);
    return startDate.getYear() + "-" + endDate.getYear();
  }
}
