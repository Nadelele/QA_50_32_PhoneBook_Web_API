package api_tests;

import dto.Contact;
import dto.ErrorMessage;
import dto.Token;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import static utils.ContactFactory.*;


public class AddContactApiTests implements BaseApi, ILogin {
    Token token;
    SoftAssert softAssert = new SoftAssert();
    ErrorMessage errorMessage;

    @BeforeClass
    public void login() {
        token = loginGetToken();
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
        Response response = getResponse(ADD_CONTACT_URL, "POST", contact.getName(), token);
        errorMessage = getErrorMessage(response);
        softAssert.assertEquals(response.code(), 400, "Wrong status code");
        softAssert.assertEquals(errorMessage.getError(), "Bad Request",
                "Wrong response message");
        softAssert.assertAll();
    }

}
