package com.project.bookingya.bdd.stepdefinitions;

import com.project.bookingya.dtos.ReservationDto;
import com.project.bookingya.models.Guest;
import com.project.bookingya.models.Reservation;
import com.project.bookingya.models.Room;
import com.project.bookingya.services.GuestService;
import com.project.bookingya.services.ReservationService;
import com.project.bookingya.services.RoomService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReservationSteps {
    @Autowired
    private GuestService servicioHuesped;
    @Autowired
    private RoomService servicioHabitacion;
    @Autowired
    private ReservationService servicioReservas;
    //Declaración variables Obj
    private Guest Huesped;
    private Reservation Reserva;
    private Room Habitacion;


   @Given("the system does have a guest with identification {string} and the system does have a room with code {string}")
    public void systemDoesNotHaveGuest (String identification, String Code) {
        try {
            servicioHuesped.getByIdentification(identification);
            servicioHabitacion.getByCode(Code);
        } catch (Exception e) {
            // Guest does not exist, which is expected
        }
    }
    @When("I create a reservation with the following details:")
    public void CreateReservation (io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> rows = dataTable.asMaps();
        Map<String, String> row = rows.get(0);

        Huesped = servicioHuesped.getByIdentification(row.get("Identification"));
        Habitacion = servicioHabitacion.getByCode(row.get("Code"));

        // Definimos el patrón exacto que usamos en la tabla de Gherkin
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ReservationDto ObjReserva = new ReservationDto();
        ObjReserva.setCheckIn(LocalDateTime.parse(row.get("check_in"), formatter));
        ObjReserva.setCheckOut(LocalDateTime.parse(row.get("check_out"), formatter));
        ObjReserva.setGuestId(Huesped.getId());
        ObjReserva.setGuestsCount(Integer.valueOf(row.get("guests_count")));
        ObjReserva.setNotes(row.get("notes"));
        ObjReserva.setRoomId(Habitacion.getId());

        Reserva = servicioReservas.create(ObjReserva);



    }
    @Then("the reservation should be created successfully")
    public void ReservationCreateSuccesfully (){
        assertNotNull(Reserva);
    }
}
