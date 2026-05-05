package com.ubu.practica_pokemon.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de usuario único para que no se repitan
    @Column(nullable = false, unique = true)
    private String username;

    // La contraseña se guardará encriptada (lo haremos en el Service)
    @Column(nullable = false)
    private String password;

    // --- Getters y Setters (Necesarios para que Spring lea los datos) ---

}