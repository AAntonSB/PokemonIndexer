package com.example.pokeDex.entities;

import java.util.List;

public class UserListReponseDto {
    private List<UserResponseDto> users;

    public UserListReponseDto(List<UserResponseDto> users) {
        this.users = users;
    }

    public List<UserResponseDto> getUsers() {
        return users;
    }
}
