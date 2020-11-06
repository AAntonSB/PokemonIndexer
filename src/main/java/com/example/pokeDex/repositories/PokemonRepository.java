package com.example.pokeDex.repositories;

import com.example.pokeDex.entities.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
 public interface PokemonRepository extends MongoRepository<Pokemon, String> {
    public Pokemon findByName(String name);
}