package api_tests;

import com.google.gson.JsonParser;
import dto.Contact;
import dto.ErrorMessage;
import dto.Token;
import dto.User;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;

import static utils.PropertiesReader.*;
import static utils.ContactFactory.*;

import java.io.IOException;

public class AddContactApiTests implements BaseApi {
    Token token;
    SoftAssert softAssert = new SoftAssert();
    ErrorMessage errorMessage;

    @BeforeClass
    public void login() {

        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        Response response = getResponse(LOGIN_URL, "POST", user, null);
        //token = new Token(JsonParser.parseString(response.body().string()).getAsJsonObject().get("token").getAsString());

        try {
            token = GSON.fromJson(response.body().string(), Token.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    }

    @Test
    public void addNewContactNegativeApiTest_With_Bad_Token() {
        Contact contact = positiveContact();
        Response response = getResponse(ADD_CONTACT_URL, "POST", contact, new Token("sdfdsf"));
        Assert.assertEquals(response.code(), 401);
    }

    @Test
    public void addNewContactNegativeApiTest_Wrong_Endpoint() {
        Contact contact = positiveContact();
        Response response = getResponse(REGISTRATION_URL, "POST", contact, token);
        errorMessage = getErrorMessage(response);
        softAssert.assertEquals(response.code(), 422, "Wrong status code");
        softAssert.assertEquals(errorMessage.getError(), "Unprocessable Content",
                "Wrong response message");
        softAssert.assertAll();
    }
    @Test
    public void addNewContactNegativeApiTest_Duplicated_Item() {
        Contact contact = positiveContact();
        getResponse(ADD_CONTACT_URL, "POST", contact, token);
        Response response = getResponse(ADD_CONTACT_URL, "POST", contact, token);
        errorMessage = getErrorMessage(response);
        softAssert.assertEquals(response.code(), 409, "Wrong status code");
        softAssert.assertEquals(errorMessage.getError(), "Duplicate contact fields",
                "Wrong response message");
        softAssert.assertAll();
    }
    @Test
    public void addNewContactNegativeApiTest_Wrong_Phone_Format() {
        Contact contact = positiveContact();
        contact.setPhone("111");
        Response response = getResponse(ADD_CONTACT_URL, "POST", contact, token);
        errorMessage = getErrorMessage(response);
        softAssert.assertEquals(response.code(), 400, "Wrong status code");
        softAssert.assertEquals(errorMessage.getError(), "Bad Request",
                "Wrong response message");
        softAssert.assertAll();
    }

    @Test
    public void addNewContactNegativeApiTest_Wrong_MediaType() {
        Contact contact = positiveContact();
        Response response = getResponse(REGISTRATION_URL, "POST", contact.getName(), token);
        errorMessage = getErrorMessage(response);
        softAssert.assertEquals(response.code(), 400, "Wrong status code");
        softAssert.assertEquals(errorMessage.getError(), "Bad Request",
                "Wrong response message");
        softAssert.assertAll();
    }

}
