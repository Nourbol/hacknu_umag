package hacknu.gospodinaman.umag.supply;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplyMapper {

    Supply fromDto(SupplySaveRequest supplySaveRequest);
}
