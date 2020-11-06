package com.example.pokeDex.entities;

import java.io.Serializable;
import java.util.List;

public class ApiAbilityPokemonDto implements Serializable {

    private static final long serialVersionUID = -5102812709580639281L;

    private ApiPokeIndexDto pokemon;

    public ApiAbilityPokemonDto() {
    }

    public ApiPokeIndexDto getPokemon() {
        return pokemon;
    }
}
