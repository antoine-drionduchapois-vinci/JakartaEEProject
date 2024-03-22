package be.vinci.pae.dao;

import be.vinci.pae.domain.Contact;
import java.util.List;

public interface ContactDAO {


  Contact create(String status, String year, int userId, int enterpriseId);

  List<Contact> readMany(int userId);

  Contact readOne(int contactId);

  Contact readOne(int userId, int enterpriseId);

  Contact update(Contact newContact);
}
