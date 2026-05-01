package com.project.bookingya.bdd.stepdefinitions;


import com.project.bookingya.dtos.RoomDto;
import com.project.bookingya.models.Room;
import com.project.bookingya.services.RoomService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

public class RoomSteps {

    @Autowired
    private RoomService servicioHabitacion;

    private Room Habitacion;

    @Given("the system does not have a room with code {string}")
    public void systemDoesNotHaveRoom (String Code){
        try {
            servicioHabitacion.getByCode(Code);
        } catch (Exception e) {

        }
    }
    @When("I create a room with the following details:")
    public void CreateRoom (io.cucumber.datatable.DataTable dataTable){

        List<Map<String, String>> rows = dataTable.asMaps();
        Map<String, String> row = rows.get(0);
        RoomDto ObjRoom = new RoomDto();
        ObjRoom.setCode(row.get("Code"));
        ObjRoom.setName(row.get("Name"));
        ObjRoom.setCity(row.get("City"));
        ObjRoom.setMaxGuests(Integer.valueOf(row.get("MaxGuest")));
        ObjRoom.setNightlyPrice(BigDecimal.valueOf(Long.parseLong(row.get("Price"))));
        ObjRoom.setAvailable(Boolean.valueOf(row.get("Available")));

        Habitacion  = servicioHabitacion.create(ObjRoom);

    }
    @Then("the room should be created successfully")
    public void RoomCreateSuccesfully (){
        assertNotNull(Habitacion);
    }
    @Then("the room code should be {string}")
    public void ValidRoomById (String Code){
        assertEquals(Code, Habitacion.getCode());
    }

}
