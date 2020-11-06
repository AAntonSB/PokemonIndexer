package com.example.pokeDex.services;

import com.example.pokeDex.entities.User;
import com.example.pokeDex.entities.UserDto;
import com.example.pokeDex.entities.UserListReponseDto;
import com.example.pokeDex.entities.UserResponseDto;
import com.example.pokeDex.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findCurrentUser() {
        // the login session is stored between page reloads,
        // and we can access the current authenticated user with this
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Object password = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return userRepo.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the user by username %s.", username)));
    }

    public UserResponseDto findUserById(String id, List<String> roles) {
        verifyUserInRoles(roles);

        var user = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new UserResponseDto(user.getUsername(), user.getId(), user.getRole());
    }

    public UserResponseDto registerUser(UserDto user, List<String> roles) {
        verifyUserInRoles(roles);

        return myUserDetailsService.addUser(user.getUsername(), user.getPassword(), user.getRole());
    }

    public void updateUser(String id, UserDto userDto, List<String> roles) {
        verifyUserInRoles(roles);

        var maybeUser = userRepo.findById(id);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepo.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the user by id %s.", id));
        }
    }

    public void deleteUserById(String id, List<String> roles) {
        verifyUserInRoles(roles);

        checkIfIdExits(id);
        userRepo.deleteById(id);
    }

    public UserListReponseDto findAll(String username, List<String> roles) {
        verifyUserInRoles(roles);

        if (username != null) {
            var user = userRepo.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the user by username %s.", username)));
            return new UserListReponseDto(List.of(user).stream().map(u -> new UserResponseDto(u.getUsername(), u.getId(), u.getRole())).collect(Collectors.toList()));
        }
        return new UserListReponseDto(userRepo.findAll().stream().map(u -> new UserResponseDto(u.getUsername(), u.getId(), u.getRole())).collect(Collectors.toList()));
    }

    private void verifyUserInRoles(List<String> roles) {
        List<String> userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        boolean matchRole = false;
        for (String role : roles) {
            if (userRoles.contains(role)) {
                matchRole = true;
                break;
            }
        }

        if (!matchRole) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private void checkIfIdExits(String id) {
        if (!userRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the user by id %s.", id));
        }
    }
}
