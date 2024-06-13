package ru.praktikum.services;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.praktikum.services.pages.HomePage;
import ru.praktikum.services.pages.LoginPage;

import static ru.praktikum.services.utils.Constants.BASEURL;


@RunWith(Parameterized.class)
public class GoToPersonAccountTest extends Hooks {
    private WebDriver driver;
    private final String browser;

    @Parameterized.Parameters(name = "Browser {0}")
    public static Object[][] browser() {
        return new Object[][]{
                {"yandex"},
                {"chrome"},
        };
    }

    public GoToPersonAccountTest(String browser) {
        this.browser = browser;
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

    @Before
    public void setup() {
        driver = getWebDriver(browser);
        driver.get(BASEURL);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Проверь переход по клику на «Личный кабинет».")
    public void LoginButtonTest() {
        HomePage homePage = new HomePage(driver);
        homePage.clickHeaderLogin(driver);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginTitleExist());
    }
}
