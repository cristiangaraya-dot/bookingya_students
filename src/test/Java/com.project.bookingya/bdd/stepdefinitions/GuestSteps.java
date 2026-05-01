package com.project.bookingya.bdd.stepdefinitions;

import com.project.bookingya.dtos.GuestDto;
import com.project.bookingya.models.Guest;
import com.project.bookingya.services.GuestService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@Component
public class GuestSteps {
    @Autowired
    private GuestService servicioHuesped ;

    private Guest Huesped;

    @Given("the system does not have a guest with identification {string}")
    public void systemDoesNotHaveGuest (String identification) {
        try {
            servicioHuesped.getByIdentification(identification);
        } catch (Exception e) {
            // Guest does not exist, which is expected
        }
    }
    @When("I create a guest with the following details:")
    public void CreateGuest (io.cucumber.datatable. DataTable dataTable){
        List<Map<String, String>> rows = dataTable.asMaps();
        Map<String, String> row = rows.get(0);
        GuestDto ObjGuest = new GuestDto();
        ObjGuest.setIdentification(row.get("Identification"));
        ObjGuest.setName(row.get("Name"));
        ObjGuest.setEmail(row.get("Email"));
        Huesped = servicioHuesped.create(ObjGuest);
    }
    @Then("the guest should be created successfully")
    public void GuestCreateSuccesfully (){
        assertNotNull(Huesped);
    }
    @Then("the guest identification should be {string}")
    public void ValidGuestByid (String Identification){
        assertEquals(Identification, Huesped.getIdentification());
    }
}
