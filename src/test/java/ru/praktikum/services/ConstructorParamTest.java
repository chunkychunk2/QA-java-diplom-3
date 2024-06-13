package ru.praktikum.services;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.praktikum.services.pages.HomePage;

import java.util.List;

import static ru.praktikum.services.utils.Constants.BASEURL;

@RunWith(Parameterized.class)
public class ConstructorParamTest {

    private WebDriver driver;

    private String browser;

    private List<String> headers;

    @Parameterized.Parameters(name = "Browser {0}, Заголовки {1}")
    public static Object[][] getData() {
        return new Object[][]{
                {"yandex", List.of("Соусы", "Булки", "Начинки")},
                {"yandex", List.of("Соусы", "Начинки", "Булки")},
                {"chrome", List.of("Начинки", "Булки", "Соусы")},
                {"chrome", List.of("Начинки", "Соусы", "Булки")}
        };
    }

    public static WebDriver getWebDriver(String browser) {
        ChromeOptions options = new ChromeOptions();
        switch (browser) {
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriverYa");
                return new ChromeDriver(options.setBinary("/opt/yandex/browser-beta/yandex-browser"));
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
                return new ChromeDriver(options);
        }
    }

    public ConstructorParamTest(String browser, List<String> headers) {
        this.browser = browser;
        this.headers = headers;
    }

    @Before
    public void setup() {
        driver = getWebDriver(browser);
        driver.get(BASEURL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    @DisplayName("Переходы к разделам по скролу: «Булки», «Соусы», «Начинки»")
    public void scrollSectionTransitionTest() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        WebElement container = driver.findElement(homePage.getContainer());
        java.util.List<WebElement> headings = container.findElements(By.xpath(".//h2"));
        for (String header : headers) {
            Assert.assertTrue(headings.contains(container.findElement(By.xpath(".//h2[text()='" + header + "']"))));
            long initialScrollPosition = (Long) ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollTop;", container);
            WebElement heading = driver.findElement(By.xpath(".//h2[text()='" + header + "']"));
            String headingText = heading.getText();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[1];", container, heading.getLocation().getY());
            Thread.sleep(1000);
            Assert.assertTrue("Переход в раздел '" + headingText + "' не работает.", homePage.isHeaderSelected(header));
            boolean inViewport = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].getBoundingClientRect().top >= 0 && arguments[0].getBoundingClientRect().bottom <= window.innerHeight;", heading);
            Assert.assertTrue("Переход в раздел '" + headingText + "' не работает.", inViewport);
            long newScrollPosition = (Long) ((JavascriptExecutor) driver).executeScript("return arguments[0].scrollTop;", container);
            Assert.assertNotEquals(initialScrollPosition, newScrollPosition);
        }
    }

    @Test
    @DisplayName("Переходы к разделам по заголовкам: «Булки», «Соусы», «Начинки»")
    public void headersSectionTransitionTest() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        for (String header : headers) {
            WebElement div = homePage.getHeader(header);
            String initialColor = div.getCssValue("box-shadow");
            homePage.clickHeader(header);
            Thread.sleep(1000);
            div = homePage.getHeader(header);
            String newColor = div.getCssValue("box-shadow");
            Assert.assertTrue(homePage.isHeaderSelected(header));
            Assert.assertNotEquals(initialColor, newColor);
        }

    }
}
