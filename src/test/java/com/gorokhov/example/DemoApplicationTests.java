package com.gorokhov.example;

import com.gorokhov.example.controller.HomeController;
import com.gorokhov.example.domain.Role;
import com.gorokhov.example.domain.User;
import com.gorokhov.example.dto.RoleDTO;
import com.gorokhov.example.dto.UserDTO;
import com.gorokhov.example.service.RoleService;
import com.gorokhov.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class DemoApplicationTests {

    @InjectMocks
    HomeController controller;

    @Mock
    UserService mockUserService;

    @Mock
    RoleService mockRoleService;

    Model model = new ConcurrentModel();

    OidcUser mockOidcUser = mock(OidcUser.class);
    OidcIdToken mockOidcIdToken = mock(OidcIdToken.class);

    User user = new User();
    Role role = new Role();
    UserDTO userDTO = new UserDTO();
    RoleDTO roleDTO = new RoleDTO();

    @BeforeEach
    void init(){

        user.setRole(role);

        when(mockOidcUser.getIdToken()).thenReturn(mockOidcIdToken);
        when(mockOidcIdToken.getTokenValue()).thenReturn("123");
        when(mockUserService.saveUser(userDTO)).thenReturn(user);
        when(mockUserService.findById(any(Long.class))).thenReturn(user);
        doNothing().when(mockUserService).deleteById(anyLong());

        when(mockRoleService.saveRole(roleDTO)).thenReturn(role);
        when(mockRoleService.findById(any(Long.class))).thenReturn(role);
        doNothing().when(mockRoleService).deleteById(anyLong());



    }

    @Test
    @DisplayName("controllerNotNull")
    void controllerNotNull() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("home")
    void home() {
        assertEquals("index",controller.home(model,mockOidcUser));
        assertTrue(model.containsAttribute("profile"));
        assertTrue(model.containsAttribute("users"));
        assertTrue(model.containsAttribute("roles"));
        assertTrue(model.containsAttribute("api_user"));
        assertTrue(model.containsAttribute("api_admin"));
        assertTrue(model.containsAttribute("api_listuser"));
        assertTrue(model.containsAttribute("api_listuser2"));
    }

    @Test
    @DisplayName("addUser")
     void addUser() {
        assertEquals("addUser",controller.addUser(model));
        assertTrue(model.containsAttribute("userDTO"));
    }

    @Test
    @DisplayName("saveUser")
    void saveUser() {
        assertEquals("redirect:/",controller.saveUser(userDTO));
    }

    @Test
    @DisplayName("editUser")
    void editUser() {
        assertEquals("editUser",controller.editUser(1L,model));
        assertTrue(model.containsAttribute("userDTO"));
    }


    @Test
    @DisplayName("deleteUser")
    void deleteUser() {
        assertEquals("redirect:/",controller.deleteUser(1L));
    }

    @Test
    @DisplayName("addRole")
    void addRole() {

        assertEquals("addRole",controller.addRole(model));
        assertTrue(model.containsAttribute("roleDTO"));

    }

    @Test
    @DisplayName("saveRole")
    void saveRole() {
        assertEquals("redirect:/",controller.saveRole(roleDTO));
    }

    @Test
    @DisplayName("editRole")
    void editRole() {
        assertEquals("editRole",controller.editRole(1L,model));
        assertTrue(model.containsAttribute("roleDTO"));
    }

    @Test
    @DisplayName("deleteRole")
    void deleteRole() {
        assertEquals("redirect:/",controller.deleteRole(1L));
    }

}