package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.HeaderMenuItem;

import java.time.Duration;

public abstract class BasePage {
    static WebDriver driver;

    public static void setDriver(WebDriver wd) {
        driver = wd;
    }

    public boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();
    }

    public boolean isTextInElement(WebElement element, String text) {
        return element.getText().contains(text);
    }

    public static <T extends BasePage> T clickButtonHeader(HeaderMenuItem item) {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(item.getLocator())));
        element.click();
        switch (item) {
            case HOME -> {
                return (T) new HomePage(driver);
            }
            case ADD -> {
                return (T) new AddPage(driver);
            }
            case ABOUT -> {
                return (T) new AboutPage(driver);
            }
            case LOGIN -> {
                return (T) new LoginPage(driver);
            }
            case CONTACTS -> {
                return (T) new ContactsPage(driver);
            }
            case SIGN_OUT -> {
                return (T) new HomePage(driver);
            }
            default -> throw new IllegalArgumentException("Invalid HeaderMenuItem");
        }
    }

    public String closeAlertReturnText(){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
}
