package com.example.pokeDex.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApiPokeIndexDto implements Serializable {
    private static final long serialVersionUID = -3356362385889455770L;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;

    public ApiPokeIndexDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ApiPokeIndexDto() {
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
