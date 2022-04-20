package com.gorokhov.example.service;

import com.gorokhov.example.domain.User;
import com.gorokhov.example.dto.UserDTO;
import com.gorokhov.example.exceptions.NotFoundException;
import com.gorokhov.example.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;


    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User saveUser(UserDTO userDTO){

        User user = new User();
        if(userDTO.getId() != null){
            user.setId(userDTO.getId());
        }
        user.setName(userDTO.getName());
        user.setRole(roleService.findByName(userDTO.getRole()));

        return userRepository.save(user);

    }

    public User findById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с идентификатором %s не найден", userId)));

    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
