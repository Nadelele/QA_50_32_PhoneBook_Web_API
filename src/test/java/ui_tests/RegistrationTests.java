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
import static utils.UserFactory.*;

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
    public void registrationPositiveTest_WithFaker(){
        User user = positiveUser();
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(new ContactsPage(getDriver())
                .isBlankAccountMessageDisplayed("No Contacts here!"));
        System.out.println(user);

    }
    @Test
    public void registrationNegativeTest_Email_WithoutDomain(){
        User user = positiveUser();
        user.setUsername("test@tutby");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText()
                .contains("minimum 2 symbols after last dot"));
    }
    @Test
    public void registrationNegativeTest_Email_WithoutStrudel(){
        User user = positiveUser();
        user.setUsername("testtut.by");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText()
                .contains("Email must contains one @"));
    }

    @Test
    public void registrationNegativeTest_Email_EmptyEmail(){
        User user = positiveUser();
       user.setUsername("");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Please enter email address!");
    }

    @Test
    public void registrationNegativeTest_Email_UserAlreadyExists(){
        User user = positiveUser();
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        new ContactsPage(getDriver()).clickBtnSignOut();
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "User already exist");
    }
    @Test
    public void registrationNegativeTest_Password_EmptyPassword(){
        User user = positiveUser();
        user.setPassword("");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password"));
    }
    @Test
    public void registrationNegativeTest_Password_WOSpecialSymbol(){
        User user = positiveUser();
        user.setPassword("QWEasd123");
        loginPage.typeLoginRegistrationFormUserDto(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password"));
    }
}
