package api_tests;

import dto.Contact;
import dto.Contacts;
import dto.Token;
import okhttp3.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;
import java.util.List;

public class DeleteContactApiTests implements BaseApi, ILogin {
    Token token;
    List<Contact> contactList;
    Response response;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        token = loginGetToken();
        response = getResponse(GET_ALL_CONTACTS_URL, "GET", null, token);
        contactList = getContactList();
    }

    @Test
    public void DeleteContactPositiveApiTest() {
        String contactId = contactList.get(contactList.size() - 1).getId();
        response = getResponse(DELETE_CONTACT_URL + contactId, "DELETE", null, token);
        softAssert.assertEquals(response.code(), 200, "Wrong response code");
        response = getResponse(GET_ALL_CONTACTS_URL, "GET", null, token);
        contactList = getContactList();
        softAssert.assertTrue(contactList.stream().filter(c -> c.getId().equals(contactId)).findFirst().isEmpty(), "Contact list still contains deleted contact");
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
