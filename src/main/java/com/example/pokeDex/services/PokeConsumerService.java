package com.example.pokeDex.services;

import com.example.pokeDex.entities.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@ConfigurationProperties(value = "example.poke-dex", ignoreUnknownFields = false)
public class PokeConsumerService {
    private final RestTemplate restTemplate;
    private String url;

    public PokeConsumerService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ApiPokeDto findPokeById(String id) {
        var urlWidthTitleQuery = url + "pokemon/" + id;

        var pokemon = restTemplate.getForObject(urlWidthTitleQuery, ApiPokeDto.class);

        if (pokemon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no pokemon found");
        }
        return pokemon;
    }

    @Cacheable("cache")
    public List<ApiPokeIndexDto> getPokeIndexList() {
        var url = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=2000";
        var allDtos = restTemplate.getForObject(url, ApiPokeIndexListDto.class);

        if (allDtos == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "NO INDEX");
        }
        return allDtos.getResults();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Cacheable("cache")
    public ApiPokeDto getPokemon(ApiPokeIndexDto apiPokeIndexDto) {
        return restTemplate.getForObject(apiPokeIndexDto.getUrl(), ApiPokeDto.class);
    }

    @Cacheable("cache")
    public List<ApiAbilityIndexDto> getAbilityIndexList() {
        var url = "https://pokeapi.co/api/v2/ability?offset=0&limit=2000";
        var allDtos = restTemplate.getForObject(url, ApiAbilityIndexListDto.class);

        if (allDtos == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "NO INDEX");
        }
        return allDtos.getResults();
    }

    public ApiAbilityDto findAbilityByIdOrName(String id) {
        var urlWidthTitleQuery = url + "ability/" + id;

        var pokemon = restTemplate.getForObject(urlWidthTitleQuery, ApiAbilityDto.class);

        if (pokemon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no ability found");
        }
        return pokemon;
    }
}
