package com.gorokhov.example.dto;

import com.gorokhov.example.domain.User;


public class UserDTO {

    private Long id;
    private String name;
    private String role;

    public UserDTO( String name, String role) {
        this.name = name;
        this.role = role;
    }

    public UserDTO(User user) {
        this.name = user.getName();
        this.role = user.getRole().getName();
        this.id = user.getId();
    }

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
