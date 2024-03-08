package com.tqs108287.App.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PurchasePage {

    WebDriver driver;
    By nameInput = By.id("inputName");
    By addressInput = By.id("address");
    By cityInput = By.id("city");
    By stateInput = By.id("state");
    By zipInput = By.id("zipCode");
    By cardTypeInput = By.id("cardType");
    By creditCardNumberInput = By.id("creditCardNumber");
    By creditCardMonthInput = By.id("creditCardMonth");
    By creditCardYearInput = By.id("creditCardYear");
    By cardNameInput = By.id("nameOnCard");
    By purchaseSubmit = By.cssSelector(".btn-primary");


    public PurchasePage(WebDriver driver) {
        this.driver = driver;
    }

    public ConfirmationPage purchaseFlight(String name, String address, String city, String state, String zip, String cardType, String creditCardNumber, String creditCardMonth, String creditCardYear, String cardName){
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(stateInput).sendKeys(state);
        driver.findElement(zipInput).sendKeys(zip);
        driver.findElement(cardTypeInput).sendKeys(cardType); // would also work new Select(driver.findElement(cardTypeInput)).selectByValue(Amex);
        driver.findElement(creditCardNumberInput).sendKeys(creditCardNumber);
        // creditCardMonthInput (aparently selenium doesn't detect Ctrl + Backspace - so i have to do this to clear the textbox)
        WebElement element = driver.findElement(creditCardMonthInput);
        element.clear(); element.sendKeys(creditCardMonth);
        // creditCardYearInput
        element = driver.findElement(creditCardYearInput);
        element.clear(); element.sendKeys(creditCardYear);
        driver.findElement(cardNameInput).sendKeys(cardName);
        driver.findElement(purchaseSubmit).click();
        return new ConfirmationPage(driver);
    }
}
