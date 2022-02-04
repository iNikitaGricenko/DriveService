package com.wolfhack.driveservice.repository;

import com.wolfhack.driveservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhone(String phone);

    boolean existsByPhone(String phone);

}
