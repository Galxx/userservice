package com.gorokhov.example.security;



import com.gorokhov.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
@Order(2)
public class SecurityConfigApi extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;


    private final UserRepository userRepository;

    public SecurityConfigApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .mvcMatchers("/api/user").hasAnyRole("USER","ADMIN")
                .mvcMatchers("/api/admin").hasRole("ADMIN")
                .mvcMatchers("/api/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().cors()
                .and().oauth2ResourceServer()
                .jwt( jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()));

    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new RoleConverter(userRepository));
        return jwtConverter;
    }

}
