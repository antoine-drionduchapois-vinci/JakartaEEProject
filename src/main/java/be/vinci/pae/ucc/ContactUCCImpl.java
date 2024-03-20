package be.vinci.pae.ucc;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.domain.ContactDTO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * Implementation of the Contact UCC interface.
 */
@Singleton
public class ContactUCCImpl implements ContactUCC {

  @Inject
  private ContactDAO myContactDAO;

  @Override
  public List<ContactDTO> getAllUsersContact(int id) {

    // Récupérer la liste complète des contacts de user depuis votre DAO
    List<ContactDTO> getAllUsersContact = myContactDAO.getAllUsersContact(id);

    return getAllUsersContact;
  }

}
