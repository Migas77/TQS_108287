package com.tqs108287.App;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(SeleniumJupiter.class)
public class BlazedemoSteps {

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

    @When("I chose my flight from {string} to {string}")
    public void iChoseMyFlightFromTo(String departureCity, String destinationCity) {
        new Select(driver.findElement(By.name("fromPort"))).selectByValue(departureCity);
        new Select(driver.findElement(By.name("toPort"))).selectByValue(destinationCity);
    }

    @And("I click submit")
    public void iClickSubmit() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("the sub-subdescription text should be {string}")
    public void theSubSubdescriptionTextShouldBe(String arg0) {
        assertEquals(driver.findElement(By.cssSelector("h3")).getText(), arg0);
    }

    @When("I chose flight number {int}")
    public void iChoseFlightNumber(int arg0) {
        driver.findElement(By.cssSelector(String.format("tr:nth-child(%d) .btn", arg0))).click();
    }

    @And("fill in my information {string} {string} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void fillInMyInformation(String name, String address, String city, String state, String zip, String cardType, String creditCardNumber, String creditCardMonth, String creditCardYear, String cardName) {
        driver.findElement(By.id("inputName")).sendKeys(name);
        driver.findElement(By.id("address")).sendKeys(address);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("state")).sendKeys(state);
        driver.findElement(By.id("zipCode")).sendKeys(zip);
        driver.findElement(By.id("cardType")).sendKeys(cardType); // would also work new Select(driver.findElement(cardTypeInput)).selectByValue(Amex);
        driver.findElement(By.id("creditCardNumber")).sendKeys(creditCardNumber);
        // creditCardMonthInput (aparently selenium doesn't detect Ctrl + Backspace - so i have to do this to clear the textbox)
        WebElement element = driver.findElement(By.id("creditCardMonth"));
        element.clear(); element.sendKeys(creditCardMonth);
        // creditCardYearInput
        element = driver.findElement(By.id("creditCardYear"));
        element.clear(); element.sendKeys(creditCardYear);
        driver.findElement(By.id("nameOnCard")).sendKeys(cardName);
    }

    @And("the title should be {string}")
    public void theTitleShouldBe(String arg0) {
        assertEquals(driver.getTitle(), arg0);
    }

    @Then("the description text should be {string}")
    public void theDescriptionTextShouldBe(String arg0) {
        assertEquals(driver.findElement(By.cssSelector("h1")).getText(), arg0);
    }

}

