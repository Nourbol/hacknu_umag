package hacknu.gospodinaman.umag.supply;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
public class SupplyController {
    
    private final SupplyService supplyService;
    
    @GetMapping
    public List<Supply> getSupplies(@RequestParam BigInteger barcode,
                                   @RequestParam Timestamp fromTime,
                                   @RequestParam Timestamp toTime) {
        return supplyService.getSupplies(barcode, fromTime, toTime);
    }
    
    @GetMapping("/{id}")
    public Supply getSupplyById(@PathVariable Long id) {
        return supplyService.getSupplyById(id);
    }
    
    @PostMapping
    public SupplyCreateResponse createSupply(@RequestBody SupplySaveRequest supplySaveRequest) {
        return supplyService.createSupply(supplySaveRequest);
    }
    
    @PutMapping("/{id}")
    public void updateSupply(@PathVariable Long id, @RequestBody SupplySaveRequest supplySaveRequest) {
        supplyService.updateSupplyById(id, supplySaveRequest);
    }
    
    @DeleteMapping("/{id}")
    public void deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupplyById(id);
    }
}
