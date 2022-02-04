package com.wolfhack.driveservice.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserCreatorDto implements Serializable {
    @NotNull @Max(150)
    private final String firstName;
    @NotNull @Max(150)
    private final String secondName;
    @NotNull @Max(150)
    private final String surname;
    @NotNull @Max(200)
    private final String city;
    @NotNull @Max(20) @Min(10)
    private final String phone;
    @NotNull @Max(500) @Min(8)
    private final String password;
    private final Set<RoleDto> roles;

    @Data
    public static class RoleDto implements Serializable {
        @NotNull
        private final Long id;
    }
}
