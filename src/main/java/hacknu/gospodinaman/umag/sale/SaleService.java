package hacknu.gospodinaman.umag.sale;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface SaleService {

    List<Sale> getSales(BigInteger barcode, Timestamp fromTime, Timestamp toTime);
    
    Sale getSaleById(Long id);
    
    SaleCreateResponse createSale(SaleSaveRequest newSale);
    
    void updateSaleById(Long id, SaleSaveRequest updatedSale);

    void deleteSaleById(Long id);
}
