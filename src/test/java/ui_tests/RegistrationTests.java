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
import utils.UserFactory;

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
}
