package com.project.bookingya.tdd;

import com.project.bookingya.dtos.GuestDto;
import com.project.bookingya.models.Guest;
import com.project.bookingya.services.GuestService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GuestTest {
    @Autowired
    private GuestService guestService;
    //@Autowired
    //private SimpleJpaRepository<GuestDto, UUID> guestEntityRepository;

    @Test // Crea huesped siempre y cuando no exista en la db
    @Order(1)
    void TestCreateGuest() throws Exception{
        GuestDto guestDto = new GuestDto();
        guestDto.setIdentification("1065915545");
        guestDto.setName("Cristian Garay Alvernia");
        guestDto.setEmail("cagaray4@gmail.com");
        Guest guest = guestService.create(guestDto);
        assertNotNull(guest);
        assertEquals(guest.getIdentification(), guestDto.getIdentification());
        assertEquals(guest.getName(), guestDto.getName());
        assertEquals(guest.getEmail(), guestDto.getEmail());

        //assertTrue(guestEntityRepository.existsById(guest.getId()));
    }

    @Test // Consulta Huespued Existente
    @Order(2)
    void TestGetGuestByIdentification() throws Exception{

        Guest SaveGuest = guestService.getByIdentification("1065915545");

        String identificacionBuscada = "";
        Guest guest = guestService.getByIdentification(SaveGuest.getIdentification());

        // 2. ¡Aquí ponemos tu mensaje de éxito para la consola!
        System.out.println("¡Búsqueda exitosa! Se encontró al huésped: " + guest.getName() + " con ID: " + guest.getIdentification());

        // 3. Validación final: Comparamos que el ID encontrado sea exactamente el que pedimos
        assertEquals(SaveGuest.getIdentification(), guest.getIdentification());
    }
}