package ru.praktikum.services.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Header {


    private By logoButton = By.xpath("//div[contains(@class,'logo')]");
    private By constructorTitle = By.xpath("//h1[text()='Соберите бургер']");
    private By headerLogin = By.xpath("//p[text()='Личный Кабинет']");

    public void clickLogoButton(WebDriver driver) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(logoButton));
        driver.findElement(logoButton).click();
    }

    public void clickHeaderLogin(WebDriver driver) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(headerLogin));
        driver.findElement(headerLogin).click();
    }

    public boolean isConstructorTitleExist(WebDriver driver) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(constructorTitle));
        return driver.findElement(constructorTitle).isDisplayed()
                && driver.findElement(constructorTitle).getText().contains("Соберите бургер");
    }
}
