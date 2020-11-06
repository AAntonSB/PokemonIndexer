package com.example.pokeDex.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ApiPokeDto implements Serializable {
    private static final long serialVersionUID = -5229665856890841182L;
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("abilities")
    private List<ApiPokeAbilityDto> abilities;

    public ApiPokeDto(String name, List<ApiPokeAbilityDto> abilities) {
        this.name = name;
        this.abilities = abilities;
    }

    public ApiPokeDto() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<ApiPokeAbilityDto> getAbilities() {
        return abilities;
    }
}
