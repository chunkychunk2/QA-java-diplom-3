package ru.praktikum.services.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
    }

    private final By loginTitle = By.xpath("//h2[text()='Вход']");
    private final By emailField = By.xpath("//label[text()='Email']//following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']//following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By newUserRegistedButton = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotPasswordButton = By.xpath("//a[text()='Восстановить пароль']");

    public void clickLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        driver.findElement(loginButton).click();
    }

    public void clickForgotPasswordButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordButton));
        driver.findElement(forgotPasswordButton).click();
    }

    public void waitUntilLoginTitleExist() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginTitle));
    }

    public boolean isLoginTitleExist() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginTitle));
        return driver.findElement(loginTitle).isDisplayed()
                && driver.findElement(loginTitle).getText().contains("Вход");
    }

    public void setEmail(String email) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(emailField));
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickNewUserRegisterButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(emailField));
        driver.findElement(newUserRegistedButton).click();
    }

    public void setPassword(String password) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        driver.findElement(passwordField).sendKeys(password);
    }
}
