package ui_tests;

import dto.User;
import manager.AppManager;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends AppManager {
    @Test
    public void loginPositiveTest() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistration("family@mail.ru", "Family123!");
        loginPage.clickBtnLoginForm();
    }

    @Test
    public void loginPositiveTestWithUserDto() {
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormUserDto(new User("family@mail.ru", "Family123!"));
        loginPage.clickBtnLoginForm();
    }

}
