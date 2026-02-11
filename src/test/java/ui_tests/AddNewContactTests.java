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
        loginPage.typeLoginRegistration("family@mail.ru",
                "Family123!");
        loginPage.clickBtnLoginForm();
        contactsPage = new ContactsPage(getDriver());
        countOfContacts = contactsPage.getCountOfContacts();
        addPage = clickButtonHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContactPositiveTest() {
        addPage.typeContactForm(positiveContact());
        int countOfContactsAfterAdd = contactsPage.getCountOfContacts();
        Assert.assertEquals(countOfContacts + 1, countOfContactsAfterAdd);
    }

    @Test(dataProvider = "dataProviderFromFile", dataProviderClass = ContactDataProvider.class)
    public void addNewContactPositiveTest_WithDataProvider(Contact contact){
        addPage.typeContactForm(contact);
        int countOfContactsAfterAdd = contactsPage.getCountOfContacts();
        Assert.assertEquals(countOfContacts + 1, countOfContactsAfterAdd);
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
