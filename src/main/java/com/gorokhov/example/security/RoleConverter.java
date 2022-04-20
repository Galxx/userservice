package com.gorokhov.example.security;

import com.gorokhov.example.domain.User;
import com.gorokhov.example.repositories.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;


import java.util.ArrayList;
import java.util.Collection;

import java.util.Optional;
import java.util.stream.Collectors;

public class RoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    UserRepository userRepository;

    public RoleConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        String userName = (String) jwt.getClaims().get("name");

        Optional<User>  optionalUser = userRepository.findByName(userName);

        ArrayList<String> realmAccess = new ArrayList<>();

        optionalUser.ifPresent(user -> realmAccess.add(user.getRole().getName()));

        return realmAccess.stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

