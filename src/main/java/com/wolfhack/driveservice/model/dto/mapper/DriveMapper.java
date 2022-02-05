package com.wolfhack.driveservice.model.dto.mapper;

import com.wolfhack.driveservice.model.Driver;
import com.wolfhack.driveservice.model.dto.DriverDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriveMapper {

     DriveMapper INSTANCE = Mappers.getMapper(DriveMapper.class);

     DriverDto toDto(Driver driver);
     Driver toDriver(DriverDto dto);

     List<DriverDto> toDtos(List<Driver> drivers);

}
