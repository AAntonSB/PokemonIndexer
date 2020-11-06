package com.example.pokeDex.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ApiPokeIndexListDto implements Serializable {
    private static final long serialVersionUID = -6389212839872224365L;
    @JsonProperty("results")
    private List<ApiPokeIndexDto> results;

    public ApiPokeIndexListDto() {
    }

    public List<ApiPokeIndexDto> getResults() {
        return results;
    }

    public ApiPokeIndexListDto(List<ApiPokeIndexDto> results) {
        this.results = results;
    }
}
