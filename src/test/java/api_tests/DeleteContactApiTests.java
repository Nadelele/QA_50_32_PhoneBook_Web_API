package api_tests;

import dto.Contact;
import dto.Contacts;
import dto.ResponseMessage;
import dto.Token;
import okhttp3.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;
import java.util.List;

import static utils.ContactFactory.positiveContact;

public class DeleteContactApiTests implements BaseApi, ILogin {
    Token token;
    List<Contact> contactList;
    Contacts contacts;
    Response response;
    Contact contact;
    String contactId;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        token = loginGetToken();

    }
@BeforeMethod
public void getIdToDelete(){
    contact = positiveContact();
    response = getResponse(ADD_CONTACT_URL, "POST", contact, token);
    ResponseMessage responseMessage;
    try {
        responseMessage = GSON.fromJson(response.body().string(), ResponseMessage.class);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    contactId = responseMessage.getMessage().split(": ")[1];
}
    @Test
    public void DeleteContactPositiveApiTest() {
        response = getResponse(DELETE_CONTACT_URL + contactId, "DELETE", null, token);
        softAssert.assertEquals(response.code(), 200, "Wrong response code");
        response = getResponse(GET_ALL_CONTACTS_URL, "GET", null, token);
        contacts = new Contacts(getContactList());
        softAssert.assertTrue(contacts.getContacts().stream().filter(c -> c.getId().equals(contactId)).findFirst().isEmpty(), "Contact list still contains deleted contact");
        softAssert.assertAll();

    }

    private List<Contact> getContactList() {
        try {
            return contactList = GSON.fromJson(response.body().string(), Contacts.class).getContacts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
