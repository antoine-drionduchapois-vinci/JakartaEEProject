package be.vinci.pae.ucc;

import be.vinci.pae.domain.ResponsibleDTO;

/**
 * Interface representing Responsible Use Case Controller (UCC).
 */
public interface ResponsibleUCC {

  /**
   * Retrieves the responsible associated with the given enterprise ID.
   *
   * @param id The enterprise ID for which to retrieve the responsible.
   * @return The ResponsibleDTO object representing the responsible, or null if not found.
   */
  ResponsibleDTO getResponsibleByEnterpriseId(int id);

}
