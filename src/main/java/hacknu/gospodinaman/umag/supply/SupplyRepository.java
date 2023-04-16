package hacknu.gospodinaman.umag.supply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

    @Query("SELECT s FROM Supply s WHERE s.barcode = :barcode AND s.supplyTime BETWEEN :fromTime AND :toTime")
    List<Supply> getSuppliesByBarcodeInRange(BigInteger barcode, Timestamp fromTime, Timestamp toTime);

    @Query("SELECT SUM(s.quantity) FROM Supply s " +
            "WHERE s.barcode = :barcode " +
            "AND s.supplyTime < :toTime")
    Long sumProductQuantitiesFromStart(BigInteger barcode, Timestamp toTime);

    @Query("SELECT s FROM Supply s " +
            "WHERE s.barcode = :barcode " +
            "AND s.supplyTime < :currentSupplyTime " +
            "ORDER BY s.supplyTime DESC " +
            "LIMIT 1")
    Optional<Supply> getLastSupplyFromStart(BigInteger barcode, Timestamp currentSupplyTime);

    @Query("SELECT s FROM Supply s WHERE s.barcode = :barcode AND s.supplyTime < :endTime")
    List<Supply> getSuppliesBefore(BigInteger barcode, Timestamp endTime);
}
