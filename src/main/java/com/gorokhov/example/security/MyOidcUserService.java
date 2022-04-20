package com.gorokhov.example.security;

import com.gorokhov.example.domain.User;
import com.gorokhov.example.repositories.RoleRepository;
import com.gorokhov.example.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class MyOidcUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public MyOidcUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser user = super.loadUser(userRequest);
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.addAll(user.getAuthorities());

        String userName = (String) userRequest.getIdToken().getClaims().get("name");

        Optional<User> optionalUser = userRepository.findByName(userName);

        if (optionalUser.isPresent()) {

            authorities.add(new SimpleGrantedAuthority("ROLE_" + optionalUser.get().getRole().getName()));

        } else {
            User newUser = new User();
            newUser.setName(userName);
            newUser.setRole(roleRepository.findByName("USER").get());
            userRepository.save(newUser);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new DefaultOidcUser(authorities, userRequest.getIdToken(), user.getUserInfo(), "sub");

    }
}
