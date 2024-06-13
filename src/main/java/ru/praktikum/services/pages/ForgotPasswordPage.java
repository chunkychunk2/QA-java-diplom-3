package ru.praktikum.services.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPasswordPage {

    private final WebDriver driver;

    private final By forgotPasswordTitle = By.xpath("//h2[text()='Восстановление пароля']");
    private final By loginButton = By.xpath("//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait webDriverWait = new WebDriverWait(driver, 3);
    }

    public boolean isForgotPasswordTitleExist() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(forgotPasswordTitle));
        return driver.findElement(forgotPasswordTitle).isDisplayed()
                && driver.findElement(forgotPasswordTitle).getText().contains("Восстановление пароля");
    }

    public void clickLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        driver.findElement(loginButton).click();
    }
}
