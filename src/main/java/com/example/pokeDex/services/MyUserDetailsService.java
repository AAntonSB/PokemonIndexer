package com.example.pokeDex.services;

import com.example.pokeDex.entities.User;
import com.example.pokeDex.entities.UserResponseDto;
import com.example.pokeDex.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class MyUserDetailsService implements UserDetailsService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepo userRepo;

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    @PostConstruct
    private void createDefaultUsers() {
        if (userRepo.findByUsername("user").isEmpty()) {
            addUser("user", "password", "ROLE_ADMIN");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Could not find the user by username %s.", username)));
        if (user == null) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantedAuthorities(user));
    }

    public UserResponseDto addUser(String username, String password, String role) {
        User user = new User(username, encoder.encode(password), role);
        try {
            userRepo.save(user);
            return new UserResponseDto(username, user.getId(), role);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        var auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(user.getRole()));
        return auth;
    }
}