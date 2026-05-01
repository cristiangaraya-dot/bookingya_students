package com.project.bookingya.atdd;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.project.bookingya.models.Guest;
import com.project.bookingya.models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookingya.dtos.GuestDto;
import com.project.bookingya.dtos.ReservationDto;
import com.project.bookingya.dtos.RoomDto;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationAtddTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void CreateReservationSuccessfully() throws Exception {

        GuestDto ObjGuest = new GuestDto();
        ObjGuest.setIdentification("1065906922");
        ObjGuest.setName("Fernando Navarro");
        ObjGuest.setEmail("nando@gmail.com");

        String varGuestResponse = mockMvc.perform(post("/guest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ObjGuest)))
                .andReturn().getResponse().getContentAsString();

        UUID guestId = extractId(varGuestResponse);



        RoomDto ObjRoom = new RoomDto();
        ObjRoom.setCode("201");
        ObjRoom.setName("Doble Preferencial ATDD");
        ObjRoom.setCity("Manizales");
        ObjRoom.setMaxGuests(2);
        ObjRoom.setNightlyPrice(BigDecimal.valueOf(350000));
        ObjRoom.setAvailable(true);

        String roomResponse = mockMvc.perform(post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ObjRoom)))
                .andReturn().getResponse().getContentAsString();

        UUID roomId = extractId(roomResponse);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        ReservationDto ObjReservation = new ReservationDto();
        ObjReservation.setGuestId(guestId);
        ObjReservation.setRoomId(roomId);
        ObjReservation.setCheckIn(LocalDateTime.parse("15/05/2026 13:30", formatter));
        ObjReservation.setCheckOut(LocalDateTime.parse("17/05/2026 10:30", formatter));
        ObjReservation.setGuestsCount(2);
        ObjReservation.setNotes("Reserva ATDD");

        mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ObjReservation)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.guestId").value(guestId.toString()))
                .andExpect(jsonPath("$.roomId").value(roomId.toString()))
                .andExpect(jsonPath("$.checkIn").value("2026-05-15T13:30:00"))
                        .andExpect(jsonPath("$.checkOut").value("2026-05-17T10:30:00"))
                                .andExpect(jsonPath("$.guestsCount").value(2))
                                .andExpect(jsonPath("$.notes").value("Reserva ATDD"));
    }

    private UUID extractId(String json) throws Exception {
        return UUID.fromString(
                objectMapper.readTree(json).get("id").asText()
        );
    }
}