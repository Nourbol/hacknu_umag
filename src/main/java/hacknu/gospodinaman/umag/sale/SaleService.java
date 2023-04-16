package hacknu.gospodinaman.umag.sale;

import hacknu.gospodinaman.umag.supply.Supply;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SaleService {

    List<Sale> getSales(BigInteger barcode, Timestamp fromTime, Timestamp toTime);
    
    Sale getSaleById(Long id);
    
    SaleCreateResponse createSale(SaleSaveRequest newSale);
    
    void updateSaleById(Long id, SaleSaveRequest updatedSale);

    void deleteSaleById(Long id);

    Optional<Sale> getLastSale(BigInteger barcode);

    Optional<Sale> getLastSaleBefore(BigInteger barcode, Timestamp saleTime);

    Optional<Sale> getFirstSaleAfter(BigInteger barcode, Timestamp saleTime);

    Long getProductTotalQuantity(BigInteger barcode);
}
