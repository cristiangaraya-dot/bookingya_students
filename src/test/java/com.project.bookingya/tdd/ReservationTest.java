package com.project.bookingya.tdd;

import com.project.bookingya.dtos.GuestDto;
import com.project.bookingya.dtos.ReservationDto;
import com.project.bookingya.dtos.RoomDto;
import com.project.bookingya.entities.ReservationEntity;
import com.project.bookingya.models.Guest;
import com.project.bookingya.models.Reservation;
import com.project.bookingya.models.Room;
import com.project.bookingya.repositories.IReservationRepository;
import com.project.bookingya.services.GuestService;
import com.project.bookingya.services.ReservationService;
import com.project.bookingya.services.RoomService;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.hibernate.sql.Delete;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.RelationService;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ReservationTest {

    @Autowired
    private ReservationService ServicioReserva;

    @Autowired
    private IReservationRepository iReservationRepository;

    @Autowired
    private GuestService ServicioHuesped;

    @Autowired
    private RoomService ServicioHabitacion;

   private static UUID saveIdReserva;
   private static UUID saveIdHuesped;
   private static UUID saveIdRoom;


    //NuevaInserción para no ser dependiente
    @Test
    @Order(1)
    void TestCreateGuest() throws Exception{
        GuestDto guestDto = new GuestDto();
        guestDto.setIdentification("19585120");
        guestDto.setName("Jesus Garay");
        guestDto.setEmail("jesus@gmail.com");
        Guest guest = ServicioHuesped.create(guestDto);
        assertNotNull(guest);
        assertEquals(guest.getIdentification(), guestDto.getIdentification());
        assertEquals(guest.getName(), guestDto.getName());
        assertEquals(guest.getEmail(), guestDto.getEmail());

        saveIdHuesped = guest.getId();

    }
    @Test
    @Order(2)
    void testCreateRoom() throws Exception{
        RoomDto roomDto = new RoomDto();
        roomDto.setCode("10");
        roomDto.setName("Habitación Sencilla");
        roomDto.setCity("Aguachica");
        roomDto.setMaxGuests(1);
        roomDto.setNightlyPrice(BigDecimal.valueOf(80000));
        roomDto.setAvailable(true);
        Room room = ServicioHabitacion.create(roomDto);
        assertNotNull(room);
        assertEquals(room.getCode(), roomDto.getCode());
        assertEquals(room.getName(), roomDto.getName());
        assertEquals(room.getCity(), roomDto.getCity());

        saveIdRoom = room.getId();
    }
    // Hasta aqui
    @Test
    @Order(3)
    void TestCreateReservation () throws Exception{

        ReservationDto ObjServicioReserva  = new ReservationDto();

        ObjServicioReserva.setGuestId(saveIdHuesped);
        ObjServicioReserva.setRoomId(saveIdRoom);
        ObjServicioReserva.setCheckIn(LocalDateTime.now());
        ObjServicioReserva.setCheckOut(LocalDateTime.now().plusDays(3));
        ObjServicioReserva.setGuestsCount(Integer.valueOf("1"));
        ObjServicioReserva.setNotes("Reserva de habitación sencilla");

        //Reservation de la carpeta servicios , variable = laclaseprivadaquesecreaenesta pantalla
        Reservation ResultReserva = ServicioReserva.create(ObjServicioReserva);

        assertNotNull(ResultReserva);
        assertEquals(ResultReserva.getGuestId(), ObjServicioReserva.getGuestId());
        assertEquals(ResultReserva.getRoomId(), ObjServicioReserva.getRoomId());
        assertEquals(ResultReserva.getCheckIn(), ObjServicioReserva.getCheckIn());
        assertEquals(ResultReserva.getCheckOut(), ObjServicioReserva.getCheckOut());
        assertEquals(ResultReserva.getGuestsCount(), ObjServicioReserva.getGuestsCount());
        assertEquals(ResultReserva.getNotes(), ObjServicioReserva.getNotes());

        saveIdReserva = ResultReserva.getId(); // Guarda registro de la reserva

        System.out.println("IdReserva = " + saveIdReserva.toString());


    }
    @Test
    @Order(4)
    void shouldConsultReservationById() throws Exception{

        Reservation varConsultReservationID = ServicioReserva.getById(saveIdReserva);

        assertNotNull(varConsultReservationID);
        assertEquals(varConsultReservationID.getId(), saveIdReserva);


    }
    @Test
    @Order(5)
    void shouldConsultReservationByUser() throws Exception{

        //Guest ConsultReservation = ServicioHuesped.getByIdentification("1065915545");

        List<Reservation> ListReservas = ServicioReserva.getByGuestId(saveIdHuesped);

        System.out.println(ListReservas);

        // Assert
        assertNotNull(ListReservas);
        //assertFalse(ListReservas.isEmpty());

    }
    @Test
    @Order(6)
    void ConsultAllReservation() throws Exception{

        List<Reservation> ObtListReservation = ServicioReserva.getAll();

        assertNotNull(ObtListReservation);
        assertFalse(ObtListReservation.isEmpty());
        System.out.println(ObtListReservation);

    }//
    @Test
    @Order(7)
    void UpdateReservationWhenExists() throws Exception{

        Reservation SearchReservationById = ServicioReserva.getById(saveIdReserva);
        ReservationDto ObjUpdateReservation = new ReservationDto();

        ObjUpdateReservation.setGuestId(SearchReservationById.getGuestId());
        ObjUpdateReservation.setRoomId(SearchReservationById.getRoomId());
        ObjUpdateReservation.setCheckIn(SearchReservationById.getCheckIn());
        ObjUpdateReservation.setCheckOut(SearchReservationById.getCheckOut());
        ObjUpdateReservation.setGuestsCount(SearchReservationById.getGuestsCount());
        ObjUpdateReservation.setNotes("Modificación de Reserva Habitación Sencilla");

        Reservation ResultUpdate = ServicioReserva.update(ObjUpdateReservation, saveIdReserva);

        System.out.println("IdReserva" + saveIdReserva);

        assertNotNull(ResultUpdate);
        assertEquals(ObjUpdateReservation.getRoomId(), ResultUpdate.getRoomId());
        assertEquals(ObjUpdateReservation.getGuestId(), ResultUpdate.getGuestId());
        assertEquals(ObjUpdateReservation.getNotes(), ResultUpdate.getNotes());

    }

    @Test
    @Order(8)
    void deleteReservationWhenExists() throws Exception {

        // Elimina la reserva por medio del Id que capturamos en la variable estatica
        ServicioReserva.delete(saveIdReserva);
        //preguntamos si dicha reserva existe y la consultamos por el ID. si es falso, la elimino.
        assertFalse(iReservationRepository.existsById(saveIdReserva));

        System.out.println("IdReserva = " + saveIdReserva);
    }

}
