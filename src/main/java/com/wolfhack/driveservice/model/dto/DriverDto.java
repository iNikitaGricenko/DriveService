package com.wolfhack.driveservice.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DriverDto implements Serializable {
    private final String firstName;
    private final String secondName;
    private final String surname;
    private final String phone;
    private final String carMake;
    private final String carNumber;
}