package com.ubu.practica_pokemon.controllers;

import com.ubu.practica_pokemon.dto.PokemonDto;
import com.ubu.practica_pokemon.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    // Aquí ya NO está el @GetMapping("/") porque ya lo tienes en HomeController

    @GetMapping("/pokemon")
    public String getPokemon(@RequestParam String name, Model model) {
        // Usamos tu método buscarEnPython
        Optional<PokemonDto> pokemonOpt = pokemonService.buscarEnPython(name);

        if (pokemonOpt.isPresent()) {
            model.addAttribute("pokemon", pokemonOpt.get());
        } else {
            model.addAttribute("error", "No se encontró el Pokémon: " + name);
        }
        return "index"; // Esto carga tu archivo index.html
    }
}