package com.gorokhov.example.security;

import com.gorokhov.example.controller.LogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LogoutHandler logoutHandler;

    @Autowired
    MyOidcUserService myOidcUserService;

    public SecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .requestMatchers().antMatchers("/","/images/**","/oauth2/**","/login/**","/logout/**","/public","/error")
                .and()
                .authorizeRequests()
                .mvcMatchers("/","/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(myOidcUserService)
                .and()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .addLogoutHandler(logoutHandler);

    }




}
