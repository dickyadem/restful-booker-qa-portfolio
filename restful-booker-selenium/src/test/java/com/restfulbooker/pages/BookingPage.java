package com.restfulbooker.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class BookingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "https://automationintesting.online";

    private By roomBookNow = By.cssSelector("a[href*='/reservation/1']");
    private By reserveNowBtn = By.xpath("//button[contains(text(),'Reserve Now')]");
    private By firstname = By.name("firstname");
    private By lastname = By.name("lastname");
    private By email = By.name("email");
    private By phone = By.name("phone");
    private By returnHome = By.linkText("Return home");

    public BookingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void clickBookNow() {
        clickWhenReady(roomBookNow);
    }

    /**
     * Opens room 1's reservation page for a unique future date range so repeated
     * runs never collide with an already-booked range on the live demo site.
     */
    public void openReservationWithUniqueDates() {
        int startOffset = ThreadLocalRandom.current().nextInt(30, 365);
        LocalDate checkin = LocalDate.now().plusDays(startOffset);
        LocalDate checkout = checkin.plusDays(1);
        driver.get(BASE_URL + "/reservation/1?checkin=" + checkin + "&checkout=" + checkout);
    }

    public void clickReserveNow() {
        clickWhenReady(reserveNowBtn);
    }

    private void clickWhenReady(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        js.executeScript("arguments[0].click();", element);
    }

    public void fillForm(String fn, String ln, String em, String ph) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstname)).sendKeys(fn);
        driver.findElement(lastname).sendKeys(ln);
        driver.findElement(email).sendKeys(em);
        driver.findElement(phone).sendKeys(ph);
    }

    public boolean isBookingSuccess() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(returnHome)).isDisplayed();
    }
}