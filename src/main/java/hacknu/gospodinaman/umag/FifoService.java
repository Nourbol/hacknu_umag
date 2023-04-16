package hacknu.gospodinaman.umag;

import hacknu.gospodinaman.umag.sale.Sale;
import hacknu.gospodinaman.umag.sale.SaleRepository;
import hacknu.gospodinaman.umag.sale.SaleService;
import hacknu.gospodinaman.umag.supply.Supply;
import hacknu.gospodinaman.umag.supply.SupplyService;
import hacknu.gospodinaman.umag.supply.SupplyWithRemainder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FifoService {

    private final SaleService saleService;
    private final SaleRepository saleRepository;
    private final SupplyService supplyService;

    public void calculate(Sale sale,
                          SupplyWithRemainder lastSupplyWithRemainder) {
        List<Supply> supplies = supplyService
                .getSupplies(sale.getBarcode(), lastSupplyWithRemainder.supply().getSupplyTime(), sale.getSaleTime());
        Long saleQuantityReminder = sale.getQuantity();
        Long totalMargin = sale.getPrice() * sale.getQuantity();
        SupplyWithRemainder lastSupply = null;
        for (Supply supply: supplies) {
            if (saleQuantityReminder < supply.getQuantity()) {
                lastSupply = new SupplyWithRemainder(supply, supply.getQuantity() - saleQuantityReminder);
            }
            saleQuantityReminder = saleQuantityReminder - supply.getQuantity();
            totalMargin -= supply.getQuantity() * supply.getPrice();
        }
    }
}
