package api_tests;

import dto.User;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.UserFactory.positiveUser;

public class RegistrationApiTests implements BaseApi {
    Response response;

    @Test
    public void registrationPositiveApiTest() {
        User user = positiveUser();
        response = getResponse(user);
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void registrationNegativeApiTest_WrongPassword() {
        User user = positiveUser();
        user.setPassword("sdsds");
        response = getResponse(user);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_WrongEmail() {
        User user = positiveUser();
        user.setUsername("mailmail.ru");
        response = getResponse(user);
        Assert.assertEquals(response.code(), 400);
    }
    @Test
    public void registrationNegativeApiTest_EmptyFields() {
        User user = new User(" ", " ");
        response = getResponse(user);
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationNegativeApiTest_UserAlreadyExists() {
        User user = positiveUser();
        response = getResponse(user);
        response = getResponse(user);
        Assert.assertEquals(response.code(), 409);
    }

    private Response getResponse(User user) {
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();

        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        return response;
    }
    @Test
    public void registrationNegativeApiTest_InvalidJSONStructure() {
        Map<String, String> invalidUser = new HashMap<>();
        invalidUser.put("usrname", "test@gmail.com");
        invalidUser.put("pass", "QWEasd123!");

        RequestBody requestBody = RequestBody.create(GSON.toJson(invalidUser), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(response.code(), 400);
    }
    @Test
    public void registrationNegativeApiTest_EmptyJSON() {
        RequestBody requestBody = RequestBody.create(GSON.toJson(""), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(response.code(), 400);
    }
    @Test
    public void registrationNegativeApiTest_NotJSON() {
        User user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), MediaType.get("text/plain"));
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(response.code(), 400);
    }
    @Test
    public void registrationNegativeApiTest_WrongEndpoint() {
        User user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), MediaType.get("text/plain"));
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL + "s")
                .post(requestBody)
                .build();
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(response.code(), 403);
    }
}
