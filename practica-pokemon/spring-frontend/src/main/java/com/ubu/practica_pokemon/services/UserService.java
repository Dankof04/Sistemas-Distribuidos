package com.ubu.practica_pokemon.services;

import com.ubu.practica_pokemon.entities.UserEntity;
import com.ubu.practica_pokemon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Este método lo usa Spring Security internamente para el Login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    // Este método lo usamos nosotros para el registro
    public void registrarUsuario(String username, String password) throws Exception {
        // Validación de campos vacíos
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new Exception("El nombre de usuario y la contraseña son obligatorios.");
        }

        // Comprobación de si ya existe (La clave de tu requisito)
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("¡Error! El usuario '" + username + "' ya está registrado.");
        }

        UserEntity nuevoUsuario = new UserEntity();
        nuevoUsuario.setUsername(username);
        // ENCRIPTAMOS la contraseña antes de guardarla en Postgres
        nuevoUsuario.setPassword(passwordEncoder.encode(password));

        userRepository.save(nuevoUsuario);
    }
}
