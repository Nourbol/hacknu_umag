package hacknu.gospodinaman.umag.supply;

import hacknu.gospodinaman.umag.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository supplyRepository;
    private final SupplyMapper supplyMapper;

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
        Supply supply = supplyRepository.save(supplyMapper.fromDto(newSupply));
        return new SupplyCreateResponse(supply.getId());
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
}
