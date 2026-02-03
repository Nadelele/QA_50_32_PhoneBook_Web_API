package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

import static pages.BasePage.setDriver;

public class ContactsPage extends BasePage {
    public ContactsPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOut;
    @FindBy(xpath = "//a[@href = '/add']")
    WebElement btnAdd;
    @FindBy(xpath = "//h1[text()=' No Contacts here!']")
    WebElement blankAccountMessage;
    @FindBy(className = "contact-item_card__2SOIM")
    List<WebElement> contactsList;

    public int getCountOfContacts() {
        return contactsList.size();
    }

    public WebElement lastElement() {
        return contactsList.get(contactsList.size() - 1);
    }

    public boolean isContactPresent(Contact contact){
        boolean isPresent = false;
        for(WebElement element:contactsList){
            if (element.getText().contains(contact.getName())
                    && element.getText().contains(contact.getPhone()))
              isPresent = true;
        }
        return isPresent;
    }

    public boolean isBtnSignOutDisplayed() {
        return isElementDisplayed(btnSignOut);
    }

    public boolean isBtnAddDisplayed() {
        return isElementDisplayed(btnAdd);
    }

    public boolean isBtnSignOutDisplayed2(String text) {
        return isTextInElement(btnSignOut, text);
    }

    public boolean isBlankAccountMessageDisplayed(String text) {
        return isTextInElement(blankAccountMessage, text);
    }

    public void clickBtnSignOut() {
        btnSignOut.click();
    }
}
