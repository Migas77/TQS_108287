package com.tqs108287.App.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    WebDriver driver;
    By departureInput = By.name("fromPort");
    By destinationInput = By.name("toPort");
    By findFlightsSubmitButton = By.cssSelector(".btn-primary");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://blazedemo.com/");
    }

    public ReservePage searchWith(String departure, String destination){
        new Select(driver.findElement(departureInput)).selectByValue(departure);
        new Select(driver.findElement(destinationInput)).selectByValue(destination);
        driver.findElement(findFlightsSubmitButton).click();
        return new ReservePage(driver);
    }


}
