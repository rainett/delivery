package io.rainett.deliverybackend.mapper;

import io.rainett.deliverybackend.dto.SignUpDto;
import io.rainett.deliverybackend.dto.UserBaseDto;
import io.rainett.deliverybackend.model.UserBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserBaseDto userToDto(UserBase userBase);

    @Mapping(target = "password", ignore = true)
    UserBase signUpToUser(SignUpDto signUpDto);
}
