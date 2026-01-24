package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.HomePage;
import pages.LoginPage;
import java.util.Random;
import static utils.UserFactory.positiveUser;

public class RegistrationTests extends AppManager {
    LoginPage loginPage;
    @BeforeMethod
    public void goToRegistrationPage(){
        new HomePage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void registrationPositiveTest(){
        int i = new Random().nextInt(1000);
        User user = new User("test" + i + "@gmail.com", "QWEasd123!");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .isBlankAccountMessageDisplayed("No Contacts here!"));

    }

    @Test
    public void registrationPositiveTestWithFaker(){
        User user = positiveUser();
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .isBlankAccountMessageDisplayed("No Contacts here!"));
        System.out.println(user);

    }
    @Test
    public void registrationNegativeTestEmailFormatWithoutDomain(){
        User user = new User("test@tut", "QWEasd123!");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText()
                .contains("minimum 2 symbols after last dot"));
    }
    @Test
    public void registrationNegativeTestEmailFormatWithoutStrudel(){
        User user = new User("testtut.by", "QWEasd123!");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText()
                .contains("Email must contains one @"));
    }

    @Test
    public void registrationNegativeTestEmailFormatWithoutEmail(){
        User user = new User("", "QWEasd123!");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Please enter email address!");
    }

    @Test
    public void registrationNegativeTestUserAlreadyExists(){
        User user = positiveUser();
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        new ContactsPage(getDriver()).clickBtnSignOut();
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "User already exist");
    }
}
