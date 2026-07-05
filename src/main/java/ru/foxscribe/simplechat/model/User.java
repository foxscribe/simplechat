package ru.foxscribe.simplechat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_users")
@Setter @Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String secret;

    @ManyToMany(mappedBy = "users")
    private Set<Room> rooms = new HashSet<>();
}