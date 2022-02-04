package com.wolfhack.driveservice.service;

import com.wolfhack.driveservice.model.Role;
import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.repository.RoleRepository;
import com.wolfhack.driveservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(login);
        CustomUserDetails userDetails = null;
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        userDetails = new CustomUserDetails();
        userDetails.setUser(user);

        return userDetails;
    }

    public User add(User user) {
        if (userRepository.existsByPhone(user.getPhone())) {
            return userRepository.findByPhone(user.getPhone());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> role = roleRepository
                .findById(2L)
                .stream()
                .collect(toSet());
        user.setRoles(role);

        return userRepository.save(user);
    }

    public User get(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User get(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public boolean equals(User user) {
        String userPhone = user.getPhone();
        String userPassword = user.getPassword();

        User existedUser = userRepository.findByPhone(userPhone);
        String existedUserPassword = existedUser.getPassword();

        return existedUserPassword.equals(userPassword);
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}