package com.example.pokeDex.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApiPokeAbilityDto implements Serializable {
    private static final long serialVersionUID = 4916331416311884745L;
    @JsonProperty("ability")
    private ApiAbilityIndexDto ability;

    @JsonProperty("is_hidden")
    private Boolean isHidden;

    @JsonProperty("slot")
    private Integer slot;

    public ApiPokeAbilityDto() {
    }

    public String getName() {
        return ability.getName();
    }
}
