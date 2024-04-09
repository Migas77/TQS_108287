package com.tqs108287.app.hw1_bustickets.FunctionalTest;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class BusTicketsSteps {
    static final Logger logger = getLogger(lookup().lookupClass());
    private final WebDriver driver = new ChromeDriver();

    @After()
    public void closeBrowser() {
        driver.quit();
    }

    @Given("I am on {string}")
    public void iAmOn(String url) {
        logger.info("I am on {}", url);
        driver.get(url);
    }


    @When("I choose my flight from {string} to {string}")
    public void iChooseMyFlightFromTo(String origin_name, String destination_name) {
        logger.info("I choose my flight from {} to {}", origin_name, destination_name);
        driver.findElement(By.id("origin_city_" + origin_name)).click();
        driver.findElement(By.id("destination_city_" + destination_name)).click();
    }

    @And("I choose date 2024-06-02")
    public void iChooseDate() {
        logger.info("I choose date 2024-06-02");
        // static because couldn't figure out how to select day
        driver.findElement(By.cssSelector(".form-select:nth-child(1)")).findElement(By.xpath("//option[. = 'Jun']")).click();
        driver.findElement(By.cssSelector(".ngb-dp-week:nth-child(2) > .ngb-dp-day:nth-child(7) > .btn-light")).click();
    }

    @And("I choose currency USD")
    public void iChooseCurrency() {
        logger.info("I choose currency USD");
        WebElement dropdown = driver.findElement(By.id("currencyPicker"));
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//option[. = 'USD']"))).click();
    }

    @And("I click search trips")
    public void iClickSearchTrips() {
        logger.info("I click search trips");
        driver.findElement(By.id("search_trips_button")).click();
    }

    @Then("{int} trips should be found")
    public void shouldBeFoundTrips(int arg0) {
        logger.info("{} trips should be found", arg0);
        WebElement firstTripFound = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.tagName("a")));
        assertThat(driver.findElements(By.tagName("a"))).hasSize(arg0);
    }

    @And("found trip 1 should be presented with Route, Bus Capacity and Available Seats {string} {string} {string}")
    public void foundTripShouldPresentedWithRouteAvailableSeatsBusCapacityAndPriceOnFoundTrips(String route, String bus_capacity, String available_seats) {
        assertThat(driver.findElement(By.id("selected_trip_route")).getText()).isEqualTo(String.format("Route: %s", route));
        assertThat(driver.findElement(By.id("selected_trip_available_seats")).getText()).isEqualTo(String.format("Available Seats: %s", available_seats));
        assertThat(driver.findElement(By.id("selected_trip_bus_capacity")).getText()).isEqualTo(String.format("Bus Capacity: %s", bus_capacity));
    }

    @And("I select trip {int}")
    public void iSelectTrip(int arg0) {
    }

    @And("selected trip should have form to fill in for seatNumber clientName clientAddress")
    public void foundTripsShouldHaveFormToFillInForSeatNumberClientNameClientAddress() {
        
    }

    @And("I fill in my reservation information {int} {string} {string}")
    public void iFillInMyReservationInformation(int seat_number, String clientName, String clientAddress) {
        WebElement dropdown = driver.findElement(By.id("seatNumberPicker"));
        dropdown.findElement(By.xpath(String.format("//option[. = '%d']", seat_number))).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).sendKeys(clientName);
        driver.findElement(By.cssSelector(".ng-untouched")).click();
        driver.findElement(By.cssSelector(".ng-untouched")).sendKeys(clientAddress);
    }

    @And("make my reservation")
    public void makeMyReservation() {
        driver.findElement(By.cssSelector(".btn:nth-child(4)")).click();
    }

    @Then("should be presented successful reservation message")
    public void shouldBePresentedSucessfullReservationMessage() {
        assertThat(driver.findElement(By.cssSelector("b")).getText()).isEqualTo("Your reservation was successfull");
    }

}
