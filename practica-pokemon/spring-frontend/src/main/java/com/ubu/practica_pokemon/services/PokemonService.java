package com.ubu.practica_pokemon.services;

import com.ubu.practica_pokemon.dto.PokemonDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class PokemonService {

    @Value("${api.python.url}")
    private String pythonApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<PokemonDto> buscarEnPython(String nombre) {
        try {
            String url = pythonApiUrl + nombre.toLowerCase().trim();
            // Jackson convertirá automáticamente el JSON de Python al DTO
            PokemonDto pokemon = restTemplate.getForObject(url, PokemonDto.class);
            return Optional.ofNullable(pokemon);
        } catch (Exception e) {
            System.err.println("DEBUG: Error conectando con Python: " + e.getMessage());
            return Optional.empty();
        }
    }
}