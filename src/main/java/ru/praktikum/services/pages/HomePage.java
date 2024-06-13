package ru.praktikum.services.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;


public class HomePage extends Header {
    private WebDriver driver;

    private By container = By.xpath("//div[contains(@class,'menuContainer')]");
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public By getContainer() {
        return container;
    }

    public void clickLoginButton() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        driver.findElement(loginButton).click();
    }

    public void clickHeader(String text) {
        String xPath = "//*/div[span[text()='" + text + "']]";
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        driver.findElement(By.xpath(xPath)).click();
        isHeaderSelected(text);
    }

    public WebElement getHeader(String text) {
        String xPath = "//*/div[span[text()='" + text + "']]";
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        return driver.findElement(By.xpath(xPath));
    }

    public boolean isHeaderSelected(String text) {
        String xPath = "//*/div[span[text()='" + text + "']]";
        try {
            new WebDriverWait(driver, 3)
                    .until(ExpectedConditions.
                            attributeContains(driver.findElement(By.xpath(xPath)), "class", "current"));
        } catch (TimeoutException e) {
            assertEquals("Раздел не выбрался основным", "org.openqa.selenium.TimeoutException", e.getClass().getName());
        }
        return driver.findElement(By.xpath(xPath)).isDisplayed()
                && driver.findElement(By.xpath(xPath)).getAttribute("class").contains("current")
                && driver.findElement(By.xpath(xPath)).getCssValue("box-shadow").contains("rgb(76, 76, 255)");
    }
}