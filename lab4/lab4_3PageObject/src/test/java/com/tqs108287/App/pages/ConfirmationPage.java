package com.tqs108287.App.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage {

    WebDriver driver;
    By description = By.cssSelector("h1");

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getDescriptionText(){
        return driver.findElement(description).getText();
    }

    public String getTitleText(){
        return driver.getTitle();
    }
}
