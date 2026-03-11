package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.RetryAnalyzer;
import static utils.PropertiesReader.*;
import utils.TestNgListener;
@Listeners(TestNgListener.class)

public class LoginTests extends AppManager {
    LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void goToRegistrationPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test(groups = {"smoke", "user"})
    public void loginPositiveTest() {
        loginPage.typeLoginRegistration(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));

        loginPage.clickBtnLoginForm();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isBtnSignOutDisplayed());

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void loginPositiveTestWithUserDto() {
        loginPage.typeLoginRegistration(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isBtnAddDisplayed());
    }

    @Test(groups = "negative")
    public void LoginNegativeTest_Email_WrongEmail() {
        loginPage.typeLoginRegistrationFormUserDto(new User("familymail.ru",
                "Family123!"));
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Wrong email or password");
    }

    @Test
    public void LoginNegativeTest_Email_EmptyEmail() {
        loginPage.typeLoginRegistrationFormUserDto(new User("",
                "Family123!"));
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Wrong email or password");
    }

    @Test
    public void LoginNegativeTest_Password_WrongPassword() {
        loginPage.typeLoginRegistrationFormUserDto(new User("family@mail.ru",
                "Family123"));
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Wrong email or password");
    }

}
