package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class EditPage extends BasePage{
    public EditPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[1]")
    WebElement inputName;
    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[2]")
    WebElement inputLastName;
    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[3]")
    WebElement inputPhone;
    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[4]")
    WebElement inputEmail;
    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[5]")
    WebElement inputAddress;
    @FindBy(xpath = "//div[@class='form_form__FOqHs']/input[6]")
    WebElement inputDescription;
    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnSave;

    public void typeContactEditForm(Contact contact){
        inputName.clear();
        inputName.sendKeys(contact.getName());
        inputLastName.clear();
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.clear();
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.clear();
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.clear();
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.clear();
        inputDescription.sendKeys(contact.getDescription());
        btnSave.click();
    }
}
