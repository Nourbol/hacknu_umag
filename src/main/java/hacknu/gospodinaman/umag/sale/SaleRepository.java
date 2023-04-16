package hacknu.gospodinaman.umag.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.barcode = :barcode AND s.saleTime BETWEEN :fromTime AND :toTime")
    List<Sale> getSalesByBarcodeInRange(BigInteger barcode, Timestamp fromTime, Timestamp toTime);

    @Query("SELECT s FROM Sale s WHERE s.barcode = :barcode ORDER BY s.saleTime DESC LIMIT 1")
    Optional<Sale> getLastSale(BigInteger barcode);

    @Query("SELECT SUM(s.quantity) FROM Sale s WHERE s.barcode = :barcode")
    Long sumAllProductQuantities(BigInteger barcode);

    @Query("SELECT s FROM Sale s " +
            "WHERE s.barcode = :barcode " +
            "AND s.saleTime < :currentSaleTime " +
            "ORDER BY s.saleTime DESC " +
            "LIMIT 1")
    Optional<Sale> getLastSaleBefore(BigInteger barcode, Timestamp currentSaleTime);

    @Query("SELECT s FROM Sale s " +
            "WHERE s.barcode = :barcode " +
            "AND s.saleTime > :currentSaleTime " +
            "ORDER BY s.saleTime ASC " +
            "LIMIT 1")
    Optional<Sale> getFirstSaleAfter(BigInteger barcode, Timestamp currentSaleTime);
}
