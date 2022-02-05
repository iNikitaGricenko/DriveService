package com.wolfhack.driveservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "drivers")
@Getter @Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "second_name", nullable = false)
    private String secondName;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "car_make", nullable = false)
    private String carMake;
    @Column(name = "car_number", nullable = false)
    private String carNumber;
}
