package utils;

import com.google.gson.Gson;
import dto.Token;
import okhttp3.*;

import java.io.IOException;

public class BaseApi {
    protected static String BASE_URL = "https://contactapp-telran-backend.herokuapp.com";
    protected static String REGISTRATION_URL = "/v1/user/registration/usernamepassword";
    protected static String LOGIN_URL = "/v1/user/login/usernamepassword";
    protected static  String ADD_CONTACT_URL = "/v1/contacts";
    protected static String GET_ALL_CONTACTS_URL = "/v1/contacts";
    protected static String EDIT_CONTACT_URL = "/v1/contacts";
    protected static String DELETE_CONTACT_URL = "/v1/contacts/";

    protected static Gson GSON = new Gson();
    protected  static MediaType JSON = MediaType.get("application/json");
    protected static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    public static Response getResponse(String endpoint, Object bodyObject, Token token) {
        RequestBody requestBody = RequestBody.create(GSON.toJson(bodyObject), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(requestBody)
                .addHeader("Authorization", token != null && !token.getToken().isEmpty() ? "Bearer " + token.getToken() : "")
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (
                IOException e) {
            throw new RuntimeException();
        }
        return response;
    }
}
