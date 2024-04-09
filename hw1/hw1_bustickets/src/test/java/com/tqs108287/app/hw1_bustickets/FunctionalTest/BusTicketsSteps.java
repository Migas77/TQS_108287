package com.tqs108287.app.hw1_bustickets.FunctionalTest;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BusTicketsSteps {
    static final Logger log = getLogger(lookup().lookupClass());
    private final WebDriver driver = new ChromeDriver();

    @After()
    public void closeBrowser() {
        driver.quit();
    }

    @Given("I am on {string}")
    public void iAmOn(String url) {
        driver.get(url);
    }


    @When("I choose my flight from {string} to {string}")
    public void iChooseMyFlightFromTo(String arg0, String arg1) {
        
    }

    @And("I choose date {string}")
    public void iChooseDate(String arg0) {
        
    }

    @And("I choose currency {string}")
    public void iChooseCurrency(String arg0) {
        
    }

    @And("I click search trips")
    public void iClickSearchTrips() {
        
    }

    @Then("{string} trips should be found")
    public void shouldBeFoundTrips(String arg0) {
        
    }

    @And("found trips should be presented with Route, Available Seats, Bus Capacity and Price")
    public void foundTripsShouldPresentedWithRouteAvailableSeatsBusCapacityAndPriceOnFoundTrips() {
        
    }

    @And("found trip {string} should contain the route {string}")
    public void foundTripShouldContainTheRoute(String arg0, String arg1) {
    }

    @And("I select trip {string}")
    public void iSelectTrip(String arg0) {
    }

    @And("selected trip should have form to fill in for seatNumber clientName clientAddress")
    public void foundTripsShouldHaveFormToFillInForSeatNumberClientNameClientAddress() {
        
    }

    @And("I fill in my reservation information {string} {string} {string}")
    public void iFillInMyReservationInformation(String arg0, String arg1, String arg2) {
        
    }

    @And("make my reservation")
    public void makeMyReservation() {
        
    }

    @Then("should be presented sucessfull reservation message")
    public void shouldBePresentedSucessfullReservationMessage() {

    }

    @And("reservation message should contain Seat Number, Client Name, Client Address and Route")
    public void reservationMessageShouldContainSeatNumberClientNameClientAddressAndRoute() {
    }


    @When("I check if the details id, route, seat number, client name and client address are present")
    public void iCheckIfTheDetailsIdRouteSeatNumberClientNameAndClientAddressArePresent() {
        
    }

    @Then("details should match {string} {string} {string} {string} {string}")
    public void detailsShouldMatch(String arg0, String arg1, String arg2, String arg3, String arg4) {
    }

}
