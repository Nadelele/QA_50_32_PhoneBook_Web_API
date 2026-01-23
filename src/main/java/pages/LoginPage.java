package pages;

import dto.User;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static pages.BasePage.driver;
import static pages.BasePage.setDriver;

public class LoginPage {
    public LoginPage(WebDriver driver) {
        setDriver(driver);
        driver.get("https://telranedu.web.app/login");
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//input[@name='email']")
    WebElement fieldEmail;
    @FindBy(xpath = "//input[@name='password']")
    WebElement fieldPassword;
    @FindBy(xpath = "//button[@name='login']")
    WebElement btnLoginForm;
    @FindBy(xpath = "//button[@name='registration']")
    WebElement btnRegistrationForm;


    public void typeLoginRegistration(String email, String password) {
        fieldEmail.sendKeys(email);
        fieldPassword.sendKeys(password);
    }

    public void typeLoginRegistrationFormUserDto(User user){
        fieldEmail.sendKeys(user.getUsername());
        fieldPassword.sendKeys(user.getPassword());
    }

    public void clickBtnLoginForm() {
        btnLoginForm.click();
    }

    public void clickBtnRegistration() {
        btnRegistrationForm.click();
    }

    public String closeAlertReturnText(){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
}