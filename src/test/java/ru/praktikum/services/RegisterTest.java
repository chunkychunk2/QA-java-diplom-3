package ru.praktikum.services;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.praktikum.services.dto.UserRq;
import ru.praktikum.services.pages.HomePage;
import ru.praktikum.services.pages.LoginPage;
import ru.praktikum.services.pages.ProfilePage;
import ru.praktikum.services.pages.RegisterPage;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static ru.praktikum.services.utils.Constants.BASEURL;


@RunWith(Parameterized.class)
public class RegisterTest extends Hooks {
    private WebDriver driver;
    private final String browser;

    @Parameterized.Parameters(name = "Browser {0}")
    public static Object[][] browser() {
        return new Object[][]{
                {"yandex"},
                {"chrome"},
        };
    }

    public RegisterTest(String browser) {
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
    @DisplayName("Регистрация")
    @Description("Проверить успешную регистрацию")
    public void checkSuccessRegistrationTest() {
        HomePage homePage = new HomePage(driver);
        homePage.clickHeaderLogin(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickNewUserRegisterButton();
        String name = RandomStringUtils.randomAlphabetic(5);
        String email = RandomStringUtils.randomAlphabetic(5) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomAlphanumeric(2) + RandomStringUtils.randomAlphabetic(3);
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.setName(name);
        registerPage.setEmail(email);
        registerPage.setPassword(password);
        registerPage.clickRegisterButton();

        loginPage.waitUntilLoginTitleExist();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        homePage.clickHeaderLogin(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        assertEquals(name, profilePage.getNameField().getAttribute("value"));
        assertEquals(email.toLowerCase(), profilePage.getLoginField().getAttribute("value"));
        assertEquals("*****", profilePage.getPasswordField().getAttribute("value"));
        UserRq user = new UserRq(name, email, password);
        Response response = given()
                .body(user)
                .when()
                .post("/api/auth/login");
        response.then().assertThat().statusCode(200);

        String token = response.jsonPath()
                .getString("accessToken");

        given()
                .header("Authorization", token)
                .body(user)
                .when()
                .delete("/api/auth/user").then().assertThat()
                .statusCode(202)
                .body("success", equalTo(true))
                .body("message", equalTo("User successfully removed"));


    }

    @Test
    @DisplayName("Регистрация")
    @Description("Проверить ошибку для некорректного пароля. Минимальный пароль — шесть символов")
    public void checkRegistrationErrorTest() {
        String name = RandomStringUtils.randomAlphabetic(5);
        String email = RandomStringUtils.randomAlphabetic(5) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphabetic(3);

        HomePage homePage = new HomePage(driver);
        homePage.clickHeaderLogin(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickNewUserRegisterButton();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.setName(name);
        registerPage.setEmail(email);
        registerPage.setPassword(password);
        registerPage.clickRegisterButton();
        Assert.assertEquals("Некорректный пароль", registerPage.getWrongPasswordMessage().getText());
    }
}
