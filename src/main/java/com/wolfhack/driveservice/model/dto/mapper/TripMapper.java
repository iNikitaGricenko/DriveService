package com.wolfhack.driveservice.model.dto.mapper;

import com.wolfhack.driveservice.model.Trip;
import com.wolfhack.driveservice.model.dto.TripInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

     TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

     TripInfoDto toDto(Trip trip);
     Trip toTrip(TripInfoDto dto);

     List<TripInfoDto> toDtos(List<Trip> trips);

}
