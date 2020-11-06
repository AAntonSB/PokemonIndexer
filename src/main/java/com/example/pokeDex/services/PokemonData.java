package com.example.pokeDex.services;

import com.example.pokeDex.entities.*;
import com.example.pokeDex.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonData {
    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private PokeConsumerService pokeConsumerService;

    public List<Pokemon> findAll(String name, String ability) {
        if (name == null && ability == null) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "This api request does not support two empty parameters");
        } else if (name == null) {
            return getPokemonsWithAbilities(ability);
        } else if (ability == null) {
            return getPokemon(name);
        } else {
            return getPokemon(name).stream().filter(pokemon ->
                    pokemon.getAbilities().stream().anyMatch(x -> x.toLowerCase().contains(ability.toLowerCase()))
            ).collect(Collectors.toList());
        }
    }

    private List<Pokemon> getPokemonsWithAbilities(String ability) {
        return pokeConsumerService.getAbilityIndexList()
                .stream()
                .filter(a1 -> a1.getName().toLowerCase()
                        .contains(ability.toLowerCase()))
                .collect(Collectors.toList())
                .stream()
                .map(a -> pokeConsumerService.findAbilityByIdOrName(a.getName()))
                .collect(Collectors.toList())
                .stream()
                .map(ApiAbilityDto::getPokemon)
                .collect(Collectors.toList())
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .stream()
                .map(ApiAbilityPokemonDto::getPokemon)
                .collect(Collectors.toList())
                .stream()
                .map(pokeIndexDto -> getPokemon(pokeIndexDto.getName()))
                .collect(Collectors.toList())
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Pokemon> getPokemon(String name) {
        var pokemonIndex = pokeConsumerService.getPokeIndexList().stream().filter(pokemon -> pokemon.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        var matchingPokemon = pokemonRepository.findAll().stream().filter(pokemon -> pokemon.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        List<String> namesInDb = matchingPokemon.stream().map(Pokemon::getName).collect(Collectors.toList());

        for (ApiPokeIndexDto indexDto : pokemonIndex) {
            if (!namesInDb.contains(indexDto.getName())) {
                var newPokemonDto = pokeConsumerService.getPokemon(indexDto);
                var newPokemon = new Pokemon(newPokemonDto);
                pokemonRepository.save(newPokemon);
                matchingPokemon.add(newPokemon);
            }
        }
        return matchingPokemon;
    }

    public Pokemon findById(String id) {
        var pokemon = pokemonRepository.findById(id);
        if (pokemon.isPresent()) {
            return pokemon.get();
        }
        var newPokeDto = pokeConsumerService.findPokeById(id);
        var newPokemon = new Pokemon(newPokeDto);
        pokemonRepository.save(newPokemon);
        return newPokemon;
    }
}
