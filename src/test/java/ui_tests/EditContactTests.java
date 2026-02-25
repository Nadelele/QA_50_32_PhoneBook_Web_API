package ui_tests;

import manager.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonHeader;
import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

public class EditContactTests extends AppManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistration(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        contactsPage = new ContactsPage(getDriver());
        if (contactsPage.getCountOfContacts() == 0) {
            addPage = clickButtonHeader(HeaderMenuItem.ADD);
            addPage.typeContactForm(positiveContact());
        }

    }
    @Test
    public void editContact() {
        contactsPage.clickFirstContact();
        contactsPage.clickBtnEdit();


    }

}
