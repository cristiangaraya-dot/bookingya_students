package com.project.bookingya.bdd.runners;


import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        //objectFactory = cucumber.runtime.SerenityObjectFactory.class,
        features = "src/test/resources/Features/Guest.feature",
        glue = {"com.project.bookingya.bdd.stepdefinitions"},
        plugin = {"pretty"}
)
@SpringBootTest
public class GuestCucumberTest {

}
