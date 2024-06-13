package ru.praktikum.services.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage extends Header {
    private WebDriver driver;

    public ProfilePage(WebDriver webDriver) {
        this.driver = webDriver;
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
    }

    private final By nameField = By.xpath("//label[text()='Имя']//following-sibling::input");
    private final By loginField = By.xpath("//label[text()='Логин']//following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']//following-sibling::input");
    private final By logoutButton = By.xpath("//button[text()='Выход']");
    private final By constructorTitle = By.xpath("//p[text()='Конструктор']");
    private final By profileField = By.xpath("//a[text()='Профиль']");

    public boolean isProfilePageExist() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(profileField));
        return driver.findElement(profileField).isDisplayed()
                && driver.findElement(profileField).getText().contains("Профиль");
    }

    public WebElement getNameField() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(nameField));
        return driver.findElement(nameField);
    }

    public WebElement getLoginField() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginField));
        return driver.findElement(loginField);
    }

    public WebElement getPasswordField() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        return driver.findElement(passwordField);
    }

    public void clickLogoutButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
        driver.findElement(logoutButton).click();
    }

    public void clickConstructorTitle() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(constructorTitle));
        driver.findElement(constructorTitle).click();
    }
}
