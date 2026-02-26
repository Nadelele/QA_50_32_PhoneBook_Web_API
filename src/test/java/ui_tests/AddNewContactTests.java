package ui_tests;

import data_providers.ContactDataProvider;
import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.ContactFactory.*;
import utils.HeaderMenuItem;

import static pages.BasePage.*;
import static utils.ContactFactory.*;
import static utils.PropertiesReader.getProperty;

public class AddNewContactTests extends AppManager {
    SoftAssert softAssert = new SoftAssert();
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;
    int countOfContacts;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistration(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.getCountOfContacts();
        addPage = clickButtonHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContact_PositiveTest() {
        addPage.typeContactForm(positiveContact());
        int countOfContactsAfterAdd = contactsPage.getCountOfContacts();
        Assert.assertEquals(countOfContacts + 1, countOfContactsAfterAdd);
    }

    @Test(dataProvider = "dataProviderFromFile_Positive", dataProviderClass = ContactDataProvider.class)
    public void addNewContact_PositiveTest_WithDataProvider(Contact contact){
        addPage.typeContactForm(contact);
        int countOfContactsAfterAdd = contactsPage.getCountOfContacts();
        Assert.assertEquals(countOfContacts + 1, countOfContactsAfterAdd);
    }

    @Test(dataProvider = "dataProviderFromFile_Negative_RequiredTextFields_Empty", dataProviderClass = ContactDataProvider.class)
    public void addNewContact_NegativeTest_RequiredTextFields_Empty(Contact contact){
        addPage.typeContactForm(contact);
        Assert.assertEquals(contactsPage.closeAlertReturnText(),
                "Required fields should not be empty");
    }

    @Test(dataProvider = "dataProviderFromFile_Negative_RequiredTextFields_Security", dataProviderClass = ContactDataProvider.class)
    public void addNewContact_NegativeTest_RequiredTextFields_Security(Contact contact){
        addPage.typeContactForm(contact);
        Assert.assertEquals(contactsPage.closeAlertReturnText(),
                "Fields should not contain the following symbols: /, <, > ");
    }

    @Test(dataProvider = "dataProviderFromFile_Negative_PhoneNumber", dataProviderClass = ContactDataProvider.class)
    public void addNewContact_NegativeTest_PhoneNumber(Contact contact){
        addPage.typeContactForm(contact);
        Assert.assertEquals(contactsPage.closeAlertReturnText(),
                " Phone not valid: Phone number must contain only digits! And length min 10, max 15!");
    }

    @Test(dataProvider = "dataProviderFromFile_Negative_Email", dataProviderClass = ContactDataProvider.class)
    public void addNewContact_NegativeTest_Email(Contact contact){
        addPage.typeContactForm(contact);
        Assert.assertEquals(contactsPage.closeAlertReturnText(),
                "Email not valid: must be a well-formed email address");
    }

    @Test
    public void checkLastElement() {
        addPage.typeContactForm(positiveContact());
        System.out.println(contactsPage.lastElement().getText());
    }

    @Test
    public void isContactAdded() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        Assert.assertTrue(contactsPage.isContactPresent(contact));
    }

    @Test
    public void addNewContactPositiveTest_ScrollToLastElement() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        contactsPage.scrollToLastContact();
        contactsPage.clickLastContact();
        String text = contactsPage.getTextInContact();
        System.out.println(text);
        softAssert.assertTrue(text.contains(contact.getName()), "softAssert: contact name");
        softAssert.assertTrue(text.contains(contact.getEmail()), "softAssert: contact email");
        softAssert.assertAll();
    }
}
