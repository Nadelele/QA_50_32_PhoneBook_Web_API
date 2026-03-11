package api_tests;

import dto.User;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;
import java.util.HashMap;
import java.util.Map;

import static utils.UserFactory.positiveUser;

public class RegistrationApiTests extends BaseApi {
    Response response;

    @Test
    public void registrationPositiveApiTest() {
        User user = positiveUser();
        response = getResponse(REGISTRATION_URL, user, null);
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void registrationNegativeApiTest_WrongPassword() {
        User user = positiveUser();
        user.setPassword("sdsds");
        response = getResponse(REGISTRATION_URL, user, null);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_WrongEmail() {
        User user = positiveUser();
        user.setUsername("mailmail.ru");
        response = getResponse(REGISTRATION_URL, user, null);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_EmptyFields() {
        User user = new User(" ", " ");
        response = getResponse(REGISTRATION_URL, user, null);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_UserAlreadyExists() {
        User user = positiveUser();
        response = getResponse(REGISTRATION_URL, user, null);
        response = getResponse(REGISTRATION_URL, user, null);
        Assert.assertEquals(response.code(), 409);
    }


    @Test
    public void registrationNegativeApiTest_InvalidJSONStructure() {
        Map<String, String> invalidUser = new HashMap<>();
        invalidUser.put("usrname", "test@gmail.com");
        invalidUser.put("pass", "QWEasd123!");
        response = getResponse(REGISTRATION_URL, invalidUser, null);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_EmptyJSON() {
       response = getResponse(REGISTRATION_URL, "", null);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_NotJSON() {
        User user = positiveUser();
        response = getResponse(REGISTRATION_URL, user.getPassword(), null);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_WrongEndpoint() {
        User user = positiveUser();
       response = getResponse(REGISTRATION_URL + "s", user, null);
        Assert.assertEquals(response.code(), 403);
    }
}
