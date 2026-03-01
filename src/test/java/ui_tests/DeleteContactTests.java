package ui_tests;

import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonHeader;
import static utils.ContactFactory.positiveContact;
import static utils.PropertiesReader.getProperty;

public class DeleteContactTests extends AppManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactsPage contactsPage;
    AddPage addPage;
    String firstContactText;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistration(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        contactsPage = new ContactsPage(getDriver());
        if(contactsPage.getCountOfContacts() == 0) {
            addPage = clickButtonHeader(HeaderMenuItem.ADD);
            addPage.typeContactForm(positiveContact());
        }
    }

    @Test
    public void deleteFirstContactPositive() {
        contactsPage.clickFirstContact();
        firstContactText = contactsPage.getTextInContact();
        System.out.println(firstContactText);
        contactsPage.clickBtnRemove();
        Contact contact = positiveContact();
        contact.setName(firstContactText.split("\\R")[0]);
        contact.setPhone(firstContactText.split("\\R")[1]);
        Assert.assertFalse(contactsPage.isContactPresentInList(contact));
//        Assert.assertEquals(contactsPage.closeAlertReturnText(),
//                 "Do you want to delete this contact?"); - should be alert check first,
//      confirmation button click and then check of removed contact presence.
    }

}
