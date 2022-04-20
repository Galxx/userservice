package com.gorokhov.example;

import com.gorokhov.example.domain.Role;
import com.gorokhov.example.dto.RoleDTO;
import com.gorokhov.example.exceptions.NotFoundException;
import com.gorokhov.example.repositories.RoleRepository;
import com.gorokhov.example.service.RoleService;
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
public class RoleServiceTest {

    @InjectMocks
    RoleService roleService;

    @Mock
    RoleRepository mockRoleRepository;

    Role role = new Role();
    RoleDTO roleDTO = new RoleDTO();
    List<Role> roleList = new ArrayList<>();
    Optional<Role> optionalRole;

    @BeforeEach
    void init(){

        roleList.add(role);
        optionalRole = Optional.of(role);

        when(mockRoleRepository.findAll()).thenReturn(roleList);
        when(mockRoleRepository.save(any(Role.class))).thenReturn(role);
        doNothing().when(mockRoleRepository).deleteById(anyLong());

    }

    @Test
    @DisplayName("findAll")
    public void findAll(){
        assertEquals(roleList,roleService.findAll());
    }

    @Test
    @DisplayName("saveUser")
    public void saveUser(){
        assertEquals(role,roleService.saveRole(roleDTO));
    }

    @Test
    @DisplayName("findById")
    public void findById(){
        optionalRole = Optional.of(role);
        when(mockRoleRepository.findById(anyLong())).thenReturn(optionalRole);
        assertEquals(role,roleService.findById(1L));
    }

    @Test
    @DisplayName("findByIdException")
    public void findByIdException(){

        optionalRole = Optional.empty();
        when(mockRoleRepository.findById(anyLong())).thenReturn(optionalRole);
        assertThrows(NotFoundException.class,() -> roleService.findById(1L));
    }

    @Test
    @DisplayName("deleteById")
    public void deleteById() {
        assertDoesNotThrow(() -> roleService.deleteById(1L));
    }



}
