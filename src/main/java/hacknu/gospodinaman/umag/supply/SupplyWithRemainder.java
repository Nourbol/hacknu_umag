package hacknu.gospodinaman.umag.supply;

import java.math.BigInteger;
import java.sql.Timestamp;

public record SupplyWithRemainder(Supply supply,
                                  Long remainder) {
}
