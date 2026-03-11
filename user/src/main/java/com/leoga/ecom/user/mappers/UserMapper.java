package com.leoga.ecom.user.mappers;

import com.leoga.ecom.user.configuration.MapperConfigGlobal;
import com.leoga.ecom.user.dto.UserRequest;
import com.leoga.ecom.user.dto.UserResponse;
import com.leoga.ecom.user.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = MapperConfigGlobal.class,
        uses = {AddressMapper.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRequest userRequest);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", source = "address")
    void patchUser(UserRequest userRequest, @MappingTarget User user);
}
