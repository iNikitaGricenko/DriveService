package com.wolfhack.driveservice;

import com.wolfhack.driveservice.model.Role;
import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DriveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriveServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            User user = new User();
            user.setId(1L);
            user.setPhone("380680695374");
            user.setPassword("OneDarkWolf10");

            user.setCity("Zaporizhzhya");
            user.setFirstName("Nikita");
            user.setLastName("Gritsenko");
            user.setSurname("Evgenevich");

            Set<Role> roles = new HashSet<>();

            Role role = new Role();
            role.setId(1L);
            role.setName("ADMIN");

            userService.addRole(role);
            roles.add(role);

            role.setId(2L);
            role.setName("USER");

            userService.addRole(role);
            roles.add(role);

            user.setRoles(roles);

            userService.add(user);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
