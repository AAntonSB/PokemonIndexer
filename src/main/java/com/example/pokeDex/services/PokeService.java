package com.example.pokeDex.services;

import com.example.pokeDex.entities.PokemonDto;
import com.example.pokeDex.entities.PokemonListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PokeService {
    @Autowired
    private PokemonData pokemonData;

    public PokemonListDto findAll(String name, String ability) {
        var pokemons = pokemonData.findAll(name,  ability);
        return new PokemonListDto(pokemons.stream().map(p ->  new PokemonDto(p.getId(), p.getName(), p.getAbilities())).collect(Collectors.toList()));
    }

    public PokemonDto findById(String id) {
        var pokemon = pokemonData.findById(id);
        return new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getAbilities());
    }
}
