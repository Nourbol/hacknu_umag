package hacknu.gospodinaman.umag.supply;

import hacknu.gospodinaman.umag.exceptions.EntityNotFoundException;
import hacknu.gospodinaman.umag.sale.Sale;
import hacknu.gospodinaman.umag.sale.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository supplyRepository;
    private final SupplyMapper supplyMapper;
    private final SaleService saleService;

    @Override
    public List<Supply> getSupplies(BigInteger barcode, Timestamp fromTime, Timestamp toTime) {
        return supplyRepository.getSuppliesByBarcodeInRange(barcode, fromTime, toTime);
    }

    @Override
    public Supply getSupplyById(Long id) {
        return supplyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("supply with id: %s not found", id)));
    }

    @Override
    public SupplyCreateResponse createSupply(SupplySaveRequest newSupply) {

    }

    @Override
    public void updateSupplyById(Long id, SupplySaveRequest updatedSupply) {
        Supply supply = getSupplyById(id);
        supplyRepository.save(supply);
    }
    
    @Override
    public void deleteSupplyById(Long id) {
        getSupplyById(id);
        supplyRepository.deleteById(id);
    }

    public List<Supply> getSuppliesBefore(BigInteger barcode, Timestamp endTime) {
        return supplyRepository.getSuppliesBefore(barcode, endTime);
    }

    private List<Supply> getChunk(BigInteger barcode, Timestamp endTime) {
        Optional<Sale> saleBefore = saleService.getLastSaleBefore(barcode, endTime);
        if (saleBefore.isEmpty()) {
            return getSuppliesBefore(barcode, endTime);
        }
        return getSupplies(barcode, saleBefore.get().getSaleTime(), endTime);
    }

    private SupplyWithRemainder getFirstSupplyWithRemainder(Sale sale) {
        BigInteger barcode = sale.getBarcode();
        Optional<Sale> optionalLastSale = saleService.getLastSale(barcode);

        if (optionalLastSale.isEmpty()) {
            throw new IllegalArgumentException(String.format("there is no sales with barcode: %s in timeline", barcode));
        }

        Long initialRemainder =
                supplyRepository.sumProductQuantitiesFromStart(barcode, optionalLastSale.get().getSaleTime())
                - saleService.getProductTotalQuantity(barcode);

        SupplyWithRemainder supplyWithRemainder =
                new SupplyWithRemainder(new Supply(), optionalLastSale.get().getQuantity() + initialRemainder);

        boolean reduceIsStarted = false;
        boolean countdownIsStopped = false;
        while (!(reduceIsStarted && countdownIsStopped) && optionalLastSale.isPresent()) {
            Sale lastSale = optionalLastSale.get();
            boolean reduceIsStartedCopy = reduceIsStarted;

            if (lastSale.equals(sale)) {
                reduceIsStarted = true;
            }

            List<Supply> chunk = getChunk(barcode, lastSale.getSaleTime());

            Map.Entry<Supply, Long> supplyWithCountdown = chunk
                    .stream()
                    .reduce(
                            Map.entry(supplyWithRemainder.supply(), supplyWithRemainder.remainder()),
                            (reducedSupplyWithCountdown, currentSupply) -> {
                                Long reducedCountdown = reducedSupplyWithCountdown.getValue();
                                Long currentSupplyQuantity = currentSupply.getQuantity();
                                if (reduceIsStartedCopy && reducedCountdown < currentSupplyQuantity) {
                                    return reducedSupplyWithCountdown;
                                }
                                if (reducedCountdown == 0) return Map.entry(currentSupply, currentSupplyQuantity);
                                return Map.entry(currentSupply , reducedCountdown - currentSupplyQuantity);
                            },
                            (prevSupply, nextSupply) -> nextSupply
                    );

            supplyWithRemainder = new SupplyWithRemainder(supplyWithCountdown.getKey(), supplyWithCountdown.getValue());
            countdownIsStopped = supplyWithCountdown.getKey().getQuantity() > supplyWithCountdown.getValue();
            optionalLastSale = saleService.getLastSaleBefore(barcode, lastSale.getSaleTime());
        }
        return supplyWithRemainder;
    }
}
