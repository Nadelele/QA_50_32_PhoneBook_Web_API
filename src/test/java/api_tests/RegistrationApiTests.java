package api_tests;

import dto.User;
import net.datafaker.idnumbers.SouthAfricanIdNumber;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;
import utils.UserFactory;

import java.io.IOException;

import static utils.UserFactory.positiveUser;

public class RegistrationApiTests implements BaseApi {
    @Test
    public void registrationPositiveApiTest() {
        User user = positiveUser();
        System.out.println(user);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();

        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
     //   System.out.println(response.code());
        Assert.assertEquals(response.code(), 200);
    }
    @Test
    public void registrationNegativeApiTest() {
        User user = positiveUser();
        user.setPassword("sdsds");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();

        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(response.code(), 400);
    }
}
