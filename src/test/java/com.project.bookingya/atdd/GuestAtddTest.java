package com.project.bookingya.atdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookingya.dtos.GuestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GuestAtddTest {
    @Autowired
    private MockMvc varMockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void CreateGuestSuccessfully() throws Exception {
        GuestDto ObjGuest = new GuestDto();
        ObjGuest.setIdentification("1065893488");
        ObjGuest.setName("Leonardo Garay");
        ObjGuest.setEmail("leonardogaray@gmail.com");

        varMockMvc.perform(post("/guest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ObjGuest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identification").exists())
                .andExpect(jsonPath("$.name").value("Leonardo Garay"))
                .andExpect(jsonPath("$.email").value("leonardogaray@gmail.com"));

    }
}