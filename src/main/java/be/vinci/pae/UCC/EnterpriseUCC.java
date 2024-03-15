package be.vinci.pae.UCC;

import be.vinci.pae.domain.Enterprise;
import java.util.List;

public interface EnterpriseUCC {

  List<Enterprise> getAllEnterprises();

  Enterprise getEnterprisesByUserId(int userId);
}
