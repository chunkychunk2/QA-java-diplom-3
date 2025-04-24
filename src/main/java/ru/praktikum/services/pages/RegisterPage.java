package ru.praktikum.services.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class RegisterPage {
    private final WebDriver driver;

    public RegisterPage(WebDriver webDriver) {
        this.driver = webDriver;
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 3);
    }

    private final By nameField = By.xpath("//label[text()='Имя']//following-sibling::input");
    private final By emailField = By.xpath("//label[text()='Email']//following-sibling::input");
    private final By passwordField = By.xpath("//label[text()='Пароль']//following-sibling::input");
    private final By registedButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By wrongPasswordField = By.xpath("//p[text()='Некорректный пароль']");
    private final By registerTitle = By.xpath("//h2[text()='Регистрация']");
    private final By loginButton = By.xpath("//a[text()='Войти']");

    public boolean isRegisterTitleExist() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(registerTitle));
        return driver.findElement(registerTitle).isDisplayed()
                && driver.findElement(registerTitle).getText().contains("Регистрация");
    }

    public void setEmail(String email) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(emailField));
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPassword(String password) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        driver.findElement(passwordField).sendKeys(password);
    }

    public void setName(String name) {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(nameField));
        driver.findElement(nameField).sendKeys(name);
    }

    public WebElement getWrongPasswordMessage() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(wrongPasswordField));
        return driver.findElement(wrongPasswordField);
    }

    public void clickRegisterButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(registedButton));
        driver.findElement(registedButton).click();
    }

    public void clickLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        driver.findElement(loginButton).click();
    }
}
