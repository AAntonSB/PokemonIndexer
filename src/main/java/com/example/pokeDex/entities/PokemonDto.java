package com.example.pokeDex.entities;

import java.util.List;

public class PokemonDto {
    private String id;
    private String name;
    private List<String> abilities;

    public PokemonDto(String id, String name, List<String> abilities) {
        this.id = id;
        this.name = name;
        this.abilities = abilities;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getAbilities() {
        return abilities;
    }
}
