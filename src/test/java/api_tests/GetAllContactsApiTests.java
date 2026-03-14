package api_tests;

import dto.ErrorMessage;
import dto.Token;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

public class GetAllContactsApiTests implements BaseApi, ILogin {
    Token token;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        token = loginGetToken();
    }

    @Test
    public void getAllContactsPositiveApiTest() {
        Response response = getResponse(GET_ALL_CONTACTS_URL, "GET", null, token);
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void getAllContactsNegativeApiTest_WrongToken() {
        Response response = getResponse(GET_ALL_CONTACTS_URL, "GET", null, new Token("fdfdf"));
        softAssert.assertEquals(response.code(), 401, "wrong status code");
        try {
            ErrorMessage errorMessage = GSON.fromJson(response.body().string(), ErrorMessage.class);
            softAssert.assertEquals(errorMessage.getError(), "Unauthorized", "wrong response message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
