package com.ubu.practica_pokemon.dto;

import lombok.Data;
import java.util.List;

@Data
public class PokemonDto {
    private Long id;
    private String name;
    private String spriteUrl;
    private List<String> types;
    private List<Encounter> encounters;
    private List<PokemonDto> evolutions;
    private List<PokemonDto> varieties;

    @Data
    public static class Encounter {
        private String gamesString;    // El título del juego
        private List<String> locations; // La lista de todas sus rutas
    }
}