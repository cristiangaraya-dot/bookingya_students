package com.project.bookingya.atdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookingya.dtos.RoomDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class RoomAtddTest {
    @Autowired
    private MockMvc VarMockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void CreateRoom () throws Exception {
        RoomDto ObjRoom = new RoomDto();
        ObjRoom.setCode(String.valueOf(04));
        ObjRoom.setName("Habitación Compartida ATDD");
        ObjRoom.setCity("Manizales");
        ObjRoom.setNightlyPrice(BigDecimal.valueOf(250053.25));
        ObjRoom.setMaxGuests(5);
        ObjRoom.setAvailable(true);

        VarMockMvc.perform(MockMvcRequestBuilders.post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ObjRoom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.name").value("Habitación Compartida ATDD"))
                .andExpect(jsonPath("$.city").value("Manizales"))
                .andExpect(jsonPath("$.nightlyPrice").value(250053.25))
                .andExpect(jsonPath("$.maxGuests").value(5))
                .andExpect(jsonPath("$.available").value(true));
    }
}
