package hacknu.gospodinaman.umag.sale;

import hacknu.gospodinaman.umag.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    public List<Sale> getSales(BigInteger barcode, Timestamp fromTime, Timestamp toTime) {
        return saleRepository.getSalesByBarcodeInRange(barcode, fromTime, toTime);
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("sale with id: %s not found", id)));
    }

    @Override
    public SaleCreateResponse createSale(SaleSaveRequest newSale) {
        Sale sale = saleRepository.save(saleMapper.fromDto(newSale));
        return new SaleCreateResponse(sale.getId());
    }

    @Override
    public void updateSaleById(Long id, SaleSaveRequest updatedSale) {
        Sale sale = getSaleById(id);
        saleRepository.save(sale);
    }

    @Override
    public void deleteSaleById(Long id) {
        getSaleById(id);
        saleRepository.deleteById(id);
    }

    @Override
    public Optional<Sale> getLastSale(BigInteger barcode) {
        return saleRepository.getLastSale(barcode);
    }

    @Override
    public Optional<Sale> getLastSaleBefore(BigInteger barcode, Timestamp saleTime) {
        return saleRepository.getLastSaleBefore(barcode, saleTime);
    }

    @Override
    public Long getProductTotalQuantity(BigInteger barcode) {
        return saleRepository.sumAllProductQuantities(barcode);
    }

    @Override
    public Optional<Sale> getFirstSaleAfter(BigInteger barcode, Timestamp saleTime) {
        return saleRepository.getFirstSaleAfter(barcode, saleTime);
    }
}
