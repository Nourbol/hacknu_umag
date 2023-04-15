package hacknu.gospodinaman.umag.sale;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    Sale fromDto(SaleSaveRequest saleSaveRequest);
}
