package com.ubu.practica_pokemon.controllers;

import com.ubu.practica_pokemon.dto.PokemonDto;
import com.ubu.practica_pokemon.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String pokemonName,
                        Model model,
                        Authentication authentication) {

        // Pasamos el nombre del usuario logueado para saludarle
        model.addAttribute("username", authentication.getName());

        // Si el usuario ha escrito algo en el buscador
        if (pokemonName != null && !pokemonName.isBlank()) {
            model.addAttribute("busqueda", pokemonName);

            Optional<PokemonDto> resultado = pokemonService.buscarEnPython(pokemonName);

            if (resultado.isPresent()) {
                model.addAttribute("pokemon", resultado.get());
            } else {
                // !!! TRADUCCIÓN DEL ERROR PARA EL FRONT !!!
                // En lugar de un 404, pasamos un mensaje amigable.
                String mensajeAmistoso = "¡Vaya! Parece que el Pokémon '" + pokemonName + "' no está en nuestra base de datos. ¿Has probado con Pikachu?";
                model.addAttribute("errorBusqueda", mensajeAmistoso);
            }
        }

        return "index";
    }
}