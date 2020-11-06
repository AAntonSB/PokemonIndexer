package com.example.pokeDex.controller;

import com.example.pokeDex.entities.PokemonDto;
import com.example.pokeDex.entities.PokemonListDto;
import com.example.pokeDex.services.PokeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(description = "The pokemon controller, a repository for pokemon", name = "Pokemon")
public class PokeController {
    @Autowired
    private PokeService pokeService;

    @Operation(summary = "Finds all the pokemon in the db matching the query parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all the pokemon",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PokemonListDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid parameter(s) supplied", content = @Content),
            @ApiResponse(responseCode = "418", description = "This api request does not support two empty parameters", content = @Content)})
    @GetMapping("/pokemon")
    public ResponseEntity<PokemonListDto> list(
            @Parameter(description = "The name of the pokemon", example = "Charizard")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "The name of a ability", example = "Mold Breaker")
            @RequestParam(value = "ability", required = false) String ability) {
        var response = pokeService.findAll(name, ability);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Finds the pokemon with the matching id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the pokemon",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PokemonDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Pokemon not found", content = @Content)})
    @GetMapping("/pokemon/{id}")
    public ResponseEntity<PokemonDto> get(@Parameter(description = "The PokeApi numeric id of the pokemon to get", example = "5")@PathVariable("id") long id) {
        return ResponseEntity.ok(pokeService.findById(String.valueOf(id)));
    }
}
