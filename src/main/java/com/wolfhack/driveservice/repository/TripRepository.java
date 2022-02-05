package com.wolfhack.driveservice.repository;

import com.wolfhack.driveservice.model.Driver;
import com.wolfhack.driveservice.model.Trip;
import com.wolfhack.driveservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByInitialLatitudeAndInitialLongitude(double latitude, double longitude);

    List<Trip> findByUser(User user);

    List<Trip> findByDriver(Driver driver);
}
