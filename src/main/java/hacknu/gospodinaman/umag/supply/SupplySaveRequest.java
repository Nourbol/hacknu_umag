package hacknu.gospodinaman.umag.supply;

import java.math.BigInteger;
import java.sql.Timestamp;

public record SupplySaveRequest(BigInteger barcode,
                                Integer price,
                                Integer quantity,
                                Timestamp supplyTime) {
}
