package hacknu.gospodinaman.umag.supply;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "supply")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supply {

    @Id
    private Long id;
    private BigInteger barcode;
    private Integer quantity;
    private Long price;
    private Timestamp supplyTime;
}