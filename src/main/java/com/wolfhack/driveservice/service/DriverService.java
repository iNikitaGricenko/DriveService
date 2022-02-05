package com.wolfhack.driveservice.service;

import com.wolfhack.driveservice.model.Driver;
import com.wolfhack.driveservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.web.client.HttpClientErrorException.*;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver add(Driver driver) {
        return driverRepository.save(driver);
    }

    public void edit(Driver driver, Long id) {
        Driver foundedTrip = driverRepository.findById(id)
                .orElseThrow();
        driver.setId(foundedTrip.getId());
        driverRepository.save(driver);
    }

    public void edit(Driver driver) {
        Driver foundedTrip = driverRepository.findByPhone(driver.getPhone())
                .orElseThrow();
        driver.setId(foundedTrip.getId());
        driverRepository.save(driver);
    }

    public List<Driver> getAll() {
        return driverRepository.findAll();
    }

    public Driver getByPhone(String phone) {
        return driverRepository.findByPhone(phone)
                .orElseThrow();
    }

    public Driver getByCarNumber(String carNumber) {
        return driverRepository.findByCarNumber(carNumber)
                .orElseThrow();
    }

    public Driver get(Long id) {
        return driverRepository.findById(id)
                .orElseThrow();
    }

    public void remove(Long id) {
        driverRepository.deleteById(id);
    }

}
