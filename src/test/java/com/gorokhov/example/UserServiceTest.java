package com.gorokhov.example;

import com.gorokhov.example.domain.Role;
import com.gorokhov.example.domain.User;
import com.gorokhov.example.dto.UserDTO;
import com.gorokhov.example.exceptions.NotFoundException;
import com.gorokhov.example.repositories.UserRepository;
import com.gorokhov.example.service.RoleService;
import com.gorokhov.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository mockUserRepository;

    @Mock
    RoleService mockRoleService;

    User user = new User();
    Role role = new Role();
    UserDTO userDTO = new UserDTO();
    List<User> userList = new ArrayList<>();
    Optional<User> optionalUser;

    @BeforeEach
    void init(){

        user.setRole(role);
        userList.add(user);
        optionalUser = Optional.of(user);

        when(mockUserRepository.findAll()).thenReturn(userList);
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        doNothing().when(mockUserRepository).deleteById(anyLong());
        when(mockRoleService.findByName(any(String.class))).thenReturn(role);


    }

    @Test
    @DisplayName("findAll")
    public void findAll(){
        assertEquals(userList,userService.findAll());
    }

    @Test
    @DisplayName("saveUser")
    public void saveUser(){
        assertEquals(user,userService.saveUser(userDTO));
    }

    @Test
    @DisplayName("findById")
    public void findById(){
        optionalUser = Optional.of(user);
        when(mockUserRepository.findById(anyLong())).thenReturn(optionalUser);
        assertEquals(user,userService.findById(1L));
    }

    @Test
    @DisplayName("findByIdException")
    public void findByIdException(){

        optionalUser = Optional.empty();
        when(mockUserRepository.findById(anyLong())).thenReturn(optionalUser);
        assertThrows(NotFoundException.class,() -> userService.findById(1L));
    }

    @Test
    @DisplayName("deleteById")
    public void deleteById() {
        assertDoesNotThrow(() -> userService.deleteById(1L));
    }


}
