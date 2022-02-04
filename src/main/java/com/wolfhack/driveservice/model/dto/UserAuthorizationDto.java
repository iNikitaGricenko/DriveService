package com.wolfhack.driveservice.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserAuthorizationDto implements Serializable {
    @NotNull @Max(20) @Min(10)
    private final String phone;
    @NotNull
    @Max(500) @Min(8)
    private final String password;
}
