package hacknu.gospodinaman.umag.supply;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SupplyMapper {

    Supply fromDto(SupplySaveRequest supplySaveRequest);

    @Mapping(target = "remainder", defaultValue = "0")
    SupplyWithRemainder map(Supply supply);

    @Mapping(target = "remainder", source = "remainder")
    SupplyWithRemainder map(Supply supply, Long remainder);
}
