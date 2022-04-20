package com.gorokhov.example;


import com.gorokhov.example.controller.TestRestController;

import com.gorokhov.example.domain.User;
import com.gorokhov.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestRestController.class})
@WebMvcTest
public class ApiTest {

    private final static String TEST_USER_ID = "user-id-123";

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    TestRestController testRestController;

    @MockBean
    UserService mockUserService;

    @Test
    public void testAPI() throws Exception {
        testString("/api/user","Hi user");
        testString("/api/admin","Hi admin");
    }


    private void testString( String api,String resultString) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(api)
                        .with(user(TEST_USER_ID))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(resultString));
    }

    @Test
    public void testListUser() throws Exception{
        User user = new User();
        user.setName("mail.ru");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(mockUserService.findAll()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listuser")
                        .with(user(TEST_USER_ID))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":0,\"name\":\"mail.ru\",\"role\":null}]"));

    }

}
