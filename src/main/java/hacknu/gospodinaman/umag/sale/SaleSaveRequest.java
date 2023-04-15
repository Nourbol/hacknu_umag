package hacknu.gospodinaman.umag.sale;

import java.math.BigInteger;
import java.sql.Timestamp;

public record SaleSaveRequest(BigInteger barcode,
                              Integer price,
                              Integer quantity,
                              Timestamp saleTime) {
}
