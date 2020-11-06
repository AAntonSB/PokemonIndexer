package com.example.pokeDex.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Document(collection = "pokemon")
public class Pokemon implements Serializable {
    private static final long serialVersionUID = 2905529855204975001L;

    @Id
    private String id;
    private String name;
    private List<String> abilities;

    public Pokemon(ApiPokeDto apiPokeDto) {
        this.name = apiPokeDto.getName();

        abilities = new LinkedList<>();
        for (ApiPokeAbilityDto abilityDto:  apiPokeDto.getAbilities()) {
            abilities.add(abilityDto.getName());
        }
    }

    public Pokemon() {
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
