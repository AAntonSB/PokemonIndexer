package com.example.pokeDex.entities;

import java.io.Serializable;
import java.util.List;

public class ApiAbilityDto implements Serializable {

    private static final long serialVersionUID = -5102812709580639281L;

    private List<ApiAbilityPokemonDto> pokemon;

    public ApiAbilityDto() {
    }

    public List<ApiAbilityPokemonDto> getPokemon() {
        return pokemon;
    }
}
