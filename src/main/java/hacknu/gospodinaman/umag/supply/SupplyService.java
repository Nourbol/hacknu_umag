package hacknu.gospodinaman.umag.supply;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface SupplyService {

    List<Supply> getSupplies(BigInteger barcode, Timestamp fromTime, Timestamp toTime);
    
    Supply getSupplyById(Long id);
    
    SupplyCreateResponse createSupply(SupplySaveRequest newSupply);
    
    void updateSupplyById(Long id, SupplySaveRequest updatedSupply);

    void deleteSupplyById(Long id);

    Long get
}
