package com.fsd.librarymanagement.entity;

import jakarta.persistence.*;


import java.util.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "is required")
    @Size(min=1, message = "is required")
    @Column(unique = true)
    private String username;

    @NotNull(message = "is required")
    @Size(min=6, message = "min = 6")
    private String password;

    // Defines a many-to-many relationship between users and roles
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;// Collection of roles associated with this user
}

