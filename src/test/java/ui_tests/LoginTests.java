package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends AppManager {
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage() {
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void loginPositiveTest() {
        loginPage.typeLoginRegistration("family@mail.ru", "Family123!");
        loginPage.clickBtnLoginForm();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isBtnSignOutDisplayed());

    }

    @Test
    public void loginPositiveTestWithUserDto() {
        loginPage.typeLoginRegistrationFormUserDto(new User("family@mail.ru",
                "Family123!"));
        loginPage.clickBtnLoginForm();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.isBtnAddDisplayed());
    }

    @Test
    public void LoginNegativeTestWithWrongEmail() {
        loginPage.typeLoginRegistrationFormUserDto(new User("familymail.ru",
                "Family123!"));
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Wrong email or password");
    }

    @Test
    public void LoginNegativeTestNoEmail() {
        loginPage.typeLoginRegistrationFormUserDto(new User("",
                "Family123!"));
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Wrong email or password");
    }

    @Test
    public void LoginNegativeTestWithWrongPassword() {
        loginPage.typeLoginRegistrationFormUserDto(new User("family@mail.ru",
                "Family123"));
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Wrong email or password");
    }

}
