package com.example.pokeDex.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApiAbilityIndexDto implements Serializable {
    private static final long serialVersionUID = 4439371490868708035L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;

    public ApiAbilityIndexDto() {
    }

    public String getName() {
        return name;
    }
}
