package com.wolfhack.driveservice.repository;

import com.wolfhack.driveservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByPhone(String phone);

    Optional<Driver> findByCarNumber(String carNumber);
}
