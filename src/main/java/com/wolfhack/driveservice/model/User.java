package com.wolfhack.driveservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", length = 150)
    private String firstName;
    @Column(name = "second_name", length = 150)
    private String lastName;
    @Column(name = "surname", length = 150)
    private String surname;

    @Column(name = "city", length = 200)
    private String city;
    @Column(name = "phone", length = 17, unique = true)
    private String phone;
    @Column(name = "password", length = 500)
    private String password;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )}
    )
    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

}
