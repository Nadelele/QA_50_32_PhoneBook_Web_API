package api_tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.Contact;
import dto.Token;
import dto.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseApi;

import static utils.PropertiesReader.*;
import static utils.ContactFactory.*;

import java.io.IOException;

public class AddContactApiTests implements BaseApi {
    Token token;
    Response response;

    @BeforeClass
    public void login() {
        String body;
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();

        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            body = response.body().string();

        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        token = new Token(JsonParser.parseString(body).getAsJsonObject().get("token").getAsString());

        System.out.println(token.getToken());
    }

    @Test
    public void addNewContactPositiveApiTest() {
        Contact contact = positiveContact();
        System.out.println(contact);
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_CONTACT_URL)
                .addHeader("Authorization", "Bearer " + token.getToken())
                .post(requestBody)
                .build();
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(response.code(), 200);
    }

}
