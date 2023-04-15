package hacknu.gospodinaman.umag.supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    @Query("SELECT s FROM Supply s WHERE s.barcode = :barcode AND s.supplyTime BETWEEN :fromTime AND :toTime")
    List<Supply> getSuppliesByBarcodeInRange(BigInteger barcode, Timestamp fromTime, Timestamp toTime);
}
