package com.wolfhack.driveservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "trips")
@SQLDelete(sql = "UPDATE trips e SET e.deleted = true, e.deleted_at = now() WHERE e.id = ?")
@Getter @Setter
public class Trip {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "current_latitude")
    private double initialLatitude;
    @Column(name = "current_longitude")
    private double initialLongitude;

    @Column(name = "destination_latitude")
    private double destinationLatitude;
    @Column(name = "destination_longitude")
    private double destinationLongitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column(name = "ordered_at")
    private Date orderedAt;
    @Column(name = "started")
    private Date started;
    @Column(name = "finished")
    private Date finished;

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;
    @Column(name = "deleted_at")
    private boolean deletedAt;

}
