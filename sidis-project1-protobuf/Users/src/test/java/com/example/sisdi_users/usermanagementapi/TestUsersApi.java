package com.example.sisdi_users.usermanagementapi;


import com.example.sisdi_users.testutils.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TestUsersApi {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final JwtDecoder jwtDecoder;


    @Autowired
    public TestUsersApi(final MockMvc mockMvc, final ObjectMapper objectMapper, final JwtDecoder jwtDecoder) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.jwtDecoder = jwtDecoder;
    }


    @Test
    void testCreateUser() throws Exception {

        final CreateUserRequest goodRequest = new CreateUserRequest(
                "testuser@mail.com",
                "pass12",
                "Test",
                "123456789",
                "2000-03-02",
                "912345678",
                "Male",
                "MARKETING_DIRECTOR",
                null,
                null,
                null,
                null
        );

        final MvcResult create = this.mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, goodRequest)))
                .andExpect(status().isCreated()).andReturn();



        final UserDTO user = JsonHelper.fromJson(objectMapper, create.getResponse().getContentAsString()
                ,UserDTO.class);

        assertEquals("testuser@mail.com", user.getUsername(), "Incorrect username!");
    }

    @Test
    void testCreateUserAndLogin() throws Exception {

        final CreateUserRequest goodRequest = new CreateUserRequest(
                "testuser23@mail.com",
                "pass12",
                "Test12",
                "123456789",
                "2000-03-02",
                "912345678",
                "Male",
                "MARKETING_DIRECTOR",
                null,
                null,
                null,
                null
        );

        final MvcResult create = this.mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, goodRequest)))
                .andExpect(status().isCreated()).andReturn();


        AuthRequest request = new AuthRequest(goodRequest.getUsername(), goodRequest.getPassword());

        final MvcResult login = this.mockMvc.perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, request)))
                .andExpect(status().isOk()).andReturn();

        String token = login.getResponse().getHeader("Authorization");

        String newToken = token.replace("Bearer ", "");
        Jwt decodedToken = this.jwtDecoder.decode(newToken);
        String subject = (String) decodedToken.getClaims().get("sub");

        String[] parts = subject.split(",");
        String username = parts[1];
        assertEquals(username,request.getUsername(),"Not the right username");
    }

    @Test
    void testDesPlan() throws Exception {
        AuthRequest request = new AuthRequest("river@mail.com", "myPass13");

        final MvcResult login = this.mockMvc.perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, request)))
                .andExpect(status().isOk()).andReturn();

        String token = login.getResponse().getHeader("Authorization");

        String newToken = token.replace("Bearer ", "");
        Jwt decodedToken = this.jwtDecoder.decode(newToken);
        String subject = (String) decodedToken.getClaims().get("sub");

        String[] parts = subject.split(",");
        String username = parts[1];
        assertEquals(username,request.getUsername(),"Not the right username");

    }

    @Test
    void testCreateSubscriber() throws Exception {

        final CreateUserRequest goodRequest = new CreateUserRequest(
                "testsub@mail.com",
                "pass12",
                "Sub",
                "123456789",
                "2000-03-02",
                "912345678",
                "Male",
                "MARKETING_DIRECTOR",
                null,
                null,
                null,
                null
        );

        final MvcResult create = this.mockMvc.perform(post("/api/public/register")
                        .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, goodRequest)))
                .andExpect(status().isCreated()).andReturn();



        final UserDTO user = JsonHelper.fromJson(objectMapper, create.getResponse().getContentAsString()
                ,UserDTO.class);

        assertEquals("testsub@mail.com", user.getUsername(), "Incorrect username!");
    }

}
