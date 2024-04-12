package be.vinci.pae.domain;

/**
 * Interface representing a Internship entity.
 */
public interface Internship extends InternshipDTO {

  /**
   * Accepts the contact.
   *
   * @return True if the contact is successfully accepted, false otherwise.
   */
  boolean accept();
}
