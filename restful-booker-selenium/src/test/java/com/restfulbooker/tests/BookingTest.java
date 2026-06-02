package com.restfulbooker.tests;

import com.restfulbooker.pages.BookingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    private WebDriver driver;
    private BookingPage bookingPage;
    private static final String BASE_URL = "https://automationintesting.online";

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get(BASE_URL);
        bookingPage = new BookingPage(driver);
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    void homepageLoadsSuccessfully() {
        assertTrue(driver.getTitle().contains("Restful-booker-platform"));
    }

    @Test
    void bookNowNavigatesToReservation() {
        bookingPage.clickBookNow();
        assertTrue(driver.getCurrentUrl().contains("/reservation/"));
    }

    @Test
    void completeReservationFlow() {
        bookingPage.openReservationWithUniqueDates();
        bookingPage.clickReserveNow();
        bookingPage.fillForm("Ade", "Mahen", "test@example.com", "08123456789");
        bookingPage.clickReserveNow();
        assertTrue(bookingPage.isBookingSuccess());
    }
}