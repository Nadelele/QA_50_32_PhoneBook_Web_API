package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AboutPage extends BasePage{
    public AboutPage(WebDriver driver){
        setDriver(driver);
        driver.get("https://telranedu.web.app/about");
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10),this);
    }
}
