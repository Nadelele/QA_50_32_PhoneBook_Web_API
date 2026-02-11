package pages;

import dto.Contact;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

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
    @FindBy(xpath = "//div[@class='contact-item_card__2SOIM'][last()]")
    WebElement lastContact;
    @FindBy(xpath = "//div[@class='contact-page_leftdiv__yhyke']/div")
    WebElement divListContacts;
    @FindBy(xpath = "//div[@class='contact-item-detailed_card__50dTS']")
    WebElement contactDetailedCard;

    public String getTextInContact(){
        return contactDetailedCard.getText();
    }
    public int getCountOfContacts() {
        return contactsList.size();
    }

    public WebElement lastElement() {
        return contactsList.get(contactsList.size() - 1);
    }

    public boolean isContactPresent(Contact contact) {
        for (WebElement element : contactsList) {
            if (element.getText().contains(contact.getName())
                    && element.getText().contains(contact.getPhone()))
                return true;
        }
        return false;
    }

    public void scrollToLastContact_2() {
        Actions actions = new Actions(driver);
        actions.scrollToElement(lastElement()).perform();
    }

    public void scrollToLastContact() {
        Actions actions = new Actions(driver);
        //actions.scrollToElement(lastContact).perform();
//        int deltaY = driver.findElement(By
//                        .xpath("//div[@class='contact-page_leftdiv__yhyke']/div"))
//                .getSize().getHeight();
        int deltaY = divListContacts.getSize().getHeight();
        System.out.println("Height -->" + deltaY);
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin
                .fromElement(contactsList.get(0));
        actions.scrollFromOrigin(scrollOrigin, 0, deltaY).perform();
    }

    public void clickLastContact() {
        lastContact.click();
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
