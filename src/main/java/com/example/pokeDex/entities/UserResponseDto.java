package com.example.pokeDex.entities;

public class UserResponseDto {
    private String username;
    private String role;
    private String id;

    public UserResponseDto(String username, String id, String role) {
        this.username = username;
        this.role = role;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
