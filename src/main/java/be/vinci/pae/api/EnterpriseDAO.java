package be.vinci.pae.api;

import be.vinci.pae.domain.Enterprise;
import java.util.List;

public interface EnterpriseDAO {

  List<Enterprise> getAllEnterprises();
}
