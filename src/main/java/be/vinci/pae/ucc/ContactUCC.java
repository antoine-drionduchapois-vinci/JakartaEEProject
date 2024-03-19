package be.vinci.pae.ucc;

import be.vinci.pae.domain.ContactDTO;
import java.util.List;

public interface ContactUCC {
  
  List<ContactDTO> getAllUsersContact(int id);
}
