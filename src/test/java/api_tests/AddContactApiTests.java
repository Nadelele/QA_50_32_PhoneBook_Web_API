package api_tests;

import com.google.gson.JsonParser;
import dto.Contact;
import dto.Token;
import dto.User;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseApi;

import static utils.PropertiesReader.*;
import static utils.ContactFactory.*;

import java.io.IOException;

public class AddContactApiTests extends BaseApi {
    Token token;

    @BeforeClass
    public void login() throws IOException {

        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        Response response = getResponse(LOGIN_URL, user, null);
        token = new Token(JsonParser.parseString(response.body().string()).getAsJsonObject().get("token").getAsString());

    }

    @Test
    public void addNewContactPositiveApiTest() {
        Contact contact = positiveContact();
        Response response = getResponse(ADD_CONTACT_URL, contact, token);
        Assert.assertEquals(response.code(), 200);
    }

}
