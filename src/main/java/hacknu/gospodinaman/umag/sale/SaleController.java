package hacknu.gospodinaman.umag.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public List<Sale> getSales(@RequestParam BigInteger barcode,
                               @RequestParam Timestamp fromTime,
                               @RequestParam Timestamp toTime) {
        return saleService.getSales(barcode, fromTime, toTime);
    }

    @GetMapping("/{id}")
    public Sale getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping
    public SaleCreateResponse createSale(@RequestBody SaleSaveRequest saleSaveRequest) {
        return saleService.createSale(saleSaveRequest);
    }

    @PutMapping("/{id}")
    public void updateSale(@PathVariable Long id, @RequestBody SaleSaveRequest saleSaveRequest) {
        saleService.updateSaleById(id, saleSaveRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteSaleById(id);
    }
}
