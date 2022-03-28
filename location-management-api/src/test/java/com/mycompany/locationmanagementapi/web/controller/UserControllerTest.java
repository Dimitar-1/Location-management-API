package com.mycompany.locationmanagementapi.web.controller;

import com.mycompany.locationmanagementapi.domain.model.UserModel;
import com.mycompany.locationmanagementapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    UserModel userModel;

    @BeforeEach
    void setUp() {
        userModel = UserModel.builder()
                .id(4L)
                .email("myemail@mail.com")
                .password("password")
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Disabled()
    @Test
    void getAllUsers() {
        Set<UserModel> userModelsSet = new HashSet<>();
        userModelsSet.add(userModel);

        when(userService.findAll()).thenReturn(userModelsSet);
        assertEquals(1, userModelsSet.size());
        verify(userService).findAll();
        verify(userService,times(1)).findAll();
    }

    @Test
    void getUserById() {
        when(userService.findUserById(any())).thenReturn(userModel);
    }

    @Test
    void login() {
    }

    @Test
    void register() {
    }
}