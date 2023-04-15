package hacknu.gospodinaman.umag.sale;

import hacknu.gospodinaman.umag.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

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
}
