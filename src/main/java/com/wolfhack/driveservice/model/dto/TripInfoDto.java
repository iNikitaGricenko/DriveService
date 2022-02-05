package com.wolfhack.driveservice.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class TripInfoDto implements Serializable {
    private final double initialLatitude;
    private final double initialLongitude;

    private final double destinationLatitude;
    private final double destinationLongitude;

    private final UserDto user;
    private final DriverDto driver;

    private final Date orderedAt;
    private final Date started;
    private final Date finished;
}
