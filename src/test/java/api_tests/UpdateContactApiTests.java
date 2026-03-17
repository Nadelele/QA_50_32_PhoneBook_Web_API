package api_tests;

import dto.Contact;
import dto.Contacts;
import dto.ResponseMessage;
import dto.Token;
import okhttp3.Response;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;
import java.util.List;

import static utils.ContactFactory.positiveContact;

public class UpdateContactApiTests implements ILogin, BaseApi {
    Token token;
    Contact contact;
    Response response;
    String contactId;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        token = loginGetToken();
    }

    @BeforeMethod
    public void createContact() {
        contact = positiveContact();
        getResponse(ADD_CONTACT_URL, "POST", contact, token);
        response = getResponse(ADD_CONTACT_URL, "POST", contact, token);
        try {
            ResponseMessage responseMessage = GSON.fromJson(response.body().string(), ResponseMessage.class);
            contactId = responseMessage.getMessage().split(": ")[1];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private List<Contact> getContacts() {
        try {
            return GSON.fromJson(response.body().string(), Contacts.class).getContacts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateContactPositiveApiTest() {
        contact = positiveContact();
        contact.setId(contactId);
        response = getResponse(EDIT_CONTACT_URL, "PUT", contact, token);
        softAssert.assertEquals(response.code(), 200, "Wrong response code");
        try {
            ResponseMessage responseMessage = GSON.fromJson(response.body().string(), ResponseMessage.class);
            System.out.println(responseMessage.getMessage());
            softAssert.assertTrue(responseMessage.getMessage().contains("Contact was updated"), "Wrong response message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
