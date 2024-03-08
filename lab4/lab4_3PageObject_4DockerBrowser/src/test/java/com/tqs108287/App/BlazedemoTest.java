package com.tqs108287.App;

import com.tqs108287.App.pages.ConfirmationPage;
import com.tqs108287.App.pages.HomePage;
import com.tqs108287.App.pages.PurchasePage;
import com.tqs108287.App.pages.ReservePage;
import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BlazedemoTest {

  @Test
  @DisplayName("Blazedemo reservation test generated by seleniumIDE")
  public void blazedemoTestWithoutPageObjectPattern(ChromeDriver driver) {
    driver.get("https://blazedemo.com/");
    {
      WebElement dropdown = driver.findElement(By.name("fromPort"));
      dropdown.findElement(By.xpath("//option[. = 'Portland']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.name("toPort"));
      dropdown.findElement(By.xpath("//option[. = 'Berlin']")).click();
    }
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertThat(driver.findElement(By.cssSelector("h3")).getText(), is("Flights from Portland to Berlin:"));
    driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
    driver.findElement(By.id("inputName")).sendKeys("First Last");
    driver.findElement(By.id("address")).sendKeys("123 Main St.");
    driver.findElement(By.id("city")).sendKeys("Anytown");
    driver.findElement(By.id("state")).sendKeys("State");
    driver.findElement(By.id("zipCode")).sendKeys("12345");
    {
      WebElement dropdown = driver.findElement(By.id("cardType"));
      dropdown.findElement(By.xpath("//option[. = 'American Express']")).click();
    }
    driver.findElement(By.id("creditCardNumber")).sendKeys("12345678");
    driver.findElement(By.id("creditCardMonth")).sendKeys("12");
    driver.findElement(By.id("creditCardYear")).sendKeys("2018");
    driver.findElement(By.id("nameOnCard")).sendKeys("John Smith");
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Thank you for your purchase today!"));
    assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
  }

  @Test
  @DisplayName("Blazedemo reservation test with Page Object Pattern")
  public void blazedemoTestWithPageObjectPattern(ChromeDriver driver) {
    HomePage homePage = new HomePage(driver);
    ReservePage reservePage = homePage.searchWith("Portland", "Berlin");
    assertThat(reservePage.getDescriptionText(), is("Flights from Portland to Berlin:"));
    PurchasePage purchasePage = reservePage.reserveFlight(3);
    ConfirmationPage confirmationPage = purchasePage.purchaseFlight("First Last", "123 Main St.", "Anytown", "State", "12345", "American Express", "12345678", "12", "2018", "John Smith");
    assertThat(confirmationPage.getDescriptionText(), is("Thank you for your purchase today!"));
    assertThat(confirmationPage.getTitleText(), is("BlazeDemo Confirmation"));
  }

  @Test
  @DisplayName("Blazedemo reservation test with Page Object Pattern and with Docker Browsers")
  public void blazedemoTestWithPageObjectPatternAndDockerBrowser(@DockerBrowser(type = BrowserType.EDGE) WebDriver driver) {
    HomePage homePage = new HomePage(driver);
    ReservePage reservePage = homePage.searchWith("Portland", "Berlin");
    assertThat(reservePage.getDescriptionText(), is("Flights from Portland to Berlin:"));
    PurchasePage purchasePage = reservePage.reserveFlight(3);
    ConfirmationPage confirmationPage = purchasePage.purchaseFlight("First Last", "123 Main St.", "Anytown", "State", "12345", "American Express", "12345678", "12", "2018", "John Smith");
    assertThat(confirmationPage.getDescriptionText(), is("Thank you for your purchase today!"));
    assertThat(confirmationPage.getTitleText(), is("BlazeDemo Confirmation"));
  }
}