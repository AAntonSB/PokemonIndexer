package com.example.pokeDex.controller;

import com.example.pokeDex.entities.UserDto;
import com.example.pokeDex.entities.UserListReponseDto;
import com.example.pokeDex.entities.UserResponseDto;
import com.example.pokeDex.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/user")
@Tag(description = "The user controller, a crud repository for the users", name = "User")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "The user is not part of the right access role", content = @Content),
            @ApiResponse(responseCode = "403", description = "The User is not logged in", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to create user", content = @Content)})
    @PostMapping()
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserResponseDto> registerUser(@Parameter(description = "The user body, containing a username and a password", required = true) @Validated @RequestBody UserDto user) {
        return ResponseEntity.ok(userService.registerUser(user, Collections.singletonList("ROLE_ADMIN")));
    }

    @Operation(summary = "Finds a user by the username or returns all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user(s)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserListReponseDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "The user is not part of the right access role", content = @Content),
            @ApiResponse(responseCode = "403", description = "The User is not logged in", content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to find users", content = @Content)})
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<UserListReponseDto> findAllUsers(@RequestParam(required = false)@Parameter(description = "The username of the requested user", example = "ExampleUsername") String username) {
        return ResponseEntity.ok(userService.findAll(username, Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
    }

    @Operation(summary = "Gets a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "The user is not part of the right access role", content = @Content),
            @ApiResponse(responseCode = "403", description = "The User is not logged in", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUser(@Parameter(description = "The id of your requested user", example = "5fa25f32fa68ba5b7597a8f2") @RequestParam String id) {
        return ResponseEntity.ok(userService.findUserById(id, Arrays.asList("ROLE_ADMIN", "ROLE_USER")));
    }

    @Operation(summary = "Updates an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "The user is not part of the right access role", content = @Content),
            @ApiResponse(responseCode = "403", description = "The User is not logged in", content = @Content),
            @ApiResponse(responseCode = "400", description = "User not found", content = @Content)})
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateUser(@Parameter(description = "the user you want to update", example = "5fa25f32fa68ba5b7597a8f2") @RequestParam String id, @Parameter(description = "The username and password you want to update your user to. Both must be present to update the user") @Validated @RequestBody UserDto user) {
        userService.updateUser(id, user, Collections.singletonList("ROLE_ADMIN"));
    }

    @Operation(summary = "Deletes a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content),
            @ApiResponse(responseCode = "401", description = "The user is not part of the right access role", content = @Content),
            @ApiResponse(responseCode = "403", description = "The User is not logged in", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteUser(@Parameter(description = "The user ID", example = "5fa25f32fa68ba5b7597a8f2")@RequestParam String id) {
        userService.deleteUserById(id, Collections.singletonList("ROLE_ADMIN"));
    }
}
