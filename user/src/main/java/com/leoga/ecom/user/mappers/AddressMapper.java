package com.leoga.ecom.user.mappers;

import com.leoga.ecom.user.configuration.MapperConfigGlobal;
import com.leoga.ecom.user.dto.AddressDTO;
import com.leoga.ecom.user.model.Address;
import org.mapstruct.*;

@Mapper(config = MapperConfigGlobal.class)
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDTO(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchAddress(AddressDTO addressDTO, @MappingTarget Address address);
}
