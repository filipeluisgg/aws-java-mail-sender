package com.felipe.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipe.user.dto.UserDto;
import com.felipe.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("POST /users")
class UserControllerTest
{
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Nested
//    @DisplayName("Anonymous user")
//    class AnonymousUser
//    {
//        @Test
//        @Transactional
//        @DisplayName("With unique and valid data")
//        void createUser_whenUniqueAndValidData_thenSavesUserToDatabase() throws Exception {
//            //Arrange
//            var userCreated = new UserDto("luisFelipe", "email@gmail.com");
//            String userDtoAsJson = objectMapper.writeValueAsString(userCreated);
//
//            //Act
//            mockMvc.perform(post("/users")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(userDtoAsJson))
//                    .andExpect(status().isCreated());
//
//            //Assert (Database)
//            var userConsulted = mockMvc.perform(get("/users/luisFelipe")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString())
//                    .andExpect(status().isOk());
//
//            assertEquals(userConsulted.getName(), userCreated.name());
//            assertEquals(userConsulted.getEmail(), userCreated.email());
//        }
//
//        @Test
//        void getAllUsers() {
//        }
//    }
}

