package ui_tests;

import dto.Contact;
import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.ContactFactory.*;
import utils.HeaderMenuItem;

import static pages.BasePage.*;
import static utils.ContactFactory.*;

public class AddNewContactTests extends AppManager {
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
        Assert.assertEquals(countOfContacts+1, countOfContactsAfterAdd);
    }

    @Test
    public void checkLastElement(){
        addPage.typeContactForm(positiveContact());
        System.out.println(contactsPage.lastElement().getText());
    }

    @Test
    public void isContactAdded(){
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        Assert.assertTrue(contactsPage.isContactPresent(contact));
    }
}
