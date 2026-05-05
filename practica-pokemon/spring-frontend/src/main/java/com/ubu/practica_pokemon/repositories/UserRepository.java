package com.ubu.practica_pokemon.repositories;

import com.ubu.practica_pokemon.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Busca un usuario por su nombre.
     * Es fundamental para el login y para comprobar si un usuario ya existe
     * antes de registrarlo.
     */
    Optional<UserEntity> findByUsername(String username);
}