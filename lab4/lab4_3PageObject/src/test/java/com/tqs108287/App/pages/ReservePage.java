package com.tqs108287.App.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;

public class ReservePage {

    WebDriver driver;
    By description = By.cssSelector("h3");
    By reserveFlightSubmit;


    public ReservePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getDescriptionText(){
        return driver.findElement(description).getText();
    }

    public PurchasePage reserveFlight(int index){
        reserveFlightSubmit = By.cssSelector(String.format("tr:nth-child(%d) .btn", index));
        driver.findElement(reserveFlightSubmit).click();
        return new PurchasePage(driver);
    }






}
