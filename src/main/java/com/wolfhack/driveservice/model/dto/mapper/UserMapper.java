package com.wolfhack.driveservice.model.dto.mapper;

import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.model.dto.UserAuthorizationDto;
import com.wolfhack.driveservice.model.dto.UserCreatorDto;
import com.wolfhack.driveservice.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

     UserDto toDto(User user);
     UserAuthorizationDto toAuthorizationDto(User user);

     User toUser(UserDto dto);
     User toAuthorizationUser(UserAuthorizationDto dto);
     User toUserCreator(UserCreatorDto dto);

     List<UserDto> toDtos(List<User> usesrs);

}
