package com.example.pokeDex.repositories;

import com.example.pokeDex.entities.Pokemon;
import com.example.pokeDex.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
