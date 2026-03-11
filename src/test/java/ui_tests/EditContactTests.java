package ui_tests;

import dto.Contact;
import manager.AppManager;
import org.checkerframework.checker.units.qual.C;
import org.testng.Assert;
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
    EditPage editPage;


    @BeforeMethod(alwaysRun = true)
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

    @Test(groups = {"smoke", "contact"})
    public void editContactPositive() {
        contactsPage.clickFirstContact();
        contactsPage.clickBtnEdit();
        editPage = new EditPage(getDriver());
        Contact contact = positiveContact();
        editPage.typeContactEditForm(contact);
        Assert.assertTrue(
                contactsPage.isContactPresentInList(contact),
                "Contact was not found in contact list after editing"
        );
        contactsPage.clickEditedContact(contact);
        String contactText = contactsPage.getTextInContact();
        Contact editedContact = new Contact(contactText);
        Assert.assertEquals(
                contact,
                editedContact,
                "Edited contact does not match expected contact"
        );
    }

}
