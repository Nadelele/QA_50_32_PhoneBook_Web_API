package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {
    static WebDriver driver;
    public static void setDriver(WebDriver wd){
        driver = wd;
    }

    public boolean isElementDisplayed(WebElement element){
        return element.isDisplayed();
    }
public boolean isTextInElement(WebElement element, String text){
        return element.getText().contains(text);
}
}
