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

public class AddContactApiTests implements BaseApi {
    Token token;

    @BeforeClass
    public void login() throws IOException {

        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        Response response = getResponse(LOGIN_URL, "POST", user, null);
        //token = new Token(JsonParser.parseString(response.body().string()).getAsJsonObject().get("token").getAsString());

        token =GSON.fromJson(response.body().string(), Token.class);
    }

    @Test
    public void addNewContactPositiveApiTest() {
        Contact contact = positiveContact();
        Response response = getResponse(ADD_CONTACT_URL, "POST", contact, token);
        Assert.assertEquals(response.code(), 200);
    }
    @Test
    public void addNewContactNegativeApiTest_WO_Token() {
        Contact contact = positiveContact();
        Response response = getResponse(ADD_CONTACT_URL, "POST", contact, null);
        Assert.assertEquals(response.code(), 403);
        System.out.println(response.message());
    }
    @Test
    public void addNewContactNegativeApiTest_With_Bad_Token() {
        Contact contact = positiveContact();
        Response response = getResponse(ADD_CONTACT_URL, "POST", contact, new Token("sdfdsf"));
        Assert.assertEquals(response.code(), 401);
        System.out.println(response.message());
    }

}
