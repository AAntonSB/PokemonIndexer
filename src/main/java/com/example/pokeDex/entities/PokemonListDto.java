package com.example.pokeDex.entities;

import java.util.List;

public class PokemonListDto {
    List<PokemonDto> pokemon;

    public PokemonListDto(List<PokemonDto> pokemon) {
        this.pokemon = pokemon;
    }

    public List<PokemonDto> getPokemon() {
        return pokemon;
    }
}
