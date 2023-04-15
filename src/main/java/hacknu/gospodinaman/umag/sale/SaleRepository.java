package hacknu.gospodinaman.umag.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.barcode = :barcode AND s.saleTime BETWEEN :fromTime AND :toTime")
    List<Sale> getSalesByBarcodeInRange(BigInteger barcode, Timestamp fromTime, Timestamp toTime);
}
