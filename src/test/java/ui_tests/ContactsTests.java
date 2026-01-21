package ui_tests;

import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

public class ContactsTests extends AppManager {
    ContactsPage contactsPage;

    @BeforeMethod
    void login(){
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistration("family@mail.ru", "Family123!");
        loginPage.clickBtnLoginForm();
        contactsPage = new ContactsPage(getDriver());
    }
    @Test
    public void btnSignOutPositive(){
        Assert.assertTrue(contactsPage.isBtnSignOutDisplayed());
    }

    @Test
    public void btnAddPositive(){
        Assert.assertTrue(contactsPage.isBtnAddDisplayed());
    }
}
