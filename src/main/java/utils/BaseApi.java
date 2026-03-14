package utils;

import com.google.gson.Gson;
import dto.ErrorMessage;
import dto.ResponseMessage;
import dto.Token;
import okhttp3.*;

import java.io.IOException;

public interface BaseApi {
    String BASE_URL = "https://contactapp-telran-backend.herokuapp.com";
    String BASE_HTTP_URL = "http://contactapp-telran-backend.herokuapp.com";
    String REGISTRATION_URL = "/v1/user/registration/usernamepassword";
    String LOGIN_URL = "/v1/user/login/usernamepassword";
    String ADD_CONTACT_URL = "/v1/contacts";
    String GET_ALL_CONTACTS_URL = "/v1/contacts";
    String EDIT_CONTACT_URL = "/v1/contacts";
    String DELETE_CONTACT_URL = "/v1/contacts/";

    Gson GSON = new Gson();
    MediaType JSON = MediaType.get("application/json");
    MediaType TEXT = MediaType.get("text/plain");
    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    default Response getResponse(String endpoint, String method, Object bodyObject, Token token) {
        RequestBody requestBody = RequestBody.create(GSON.toJson(bodyObject), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .method(method, requiresBody(method) ? requestBody : null)
                .addHeader("Authorization", token != null && !token.getToken().isEmpty() ? "Bearer " + token.getToken() : "")
                .build();
        try {
           return OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
    private boolean requiresBody(String method) {
        return method.equals("POST") || method.equals("PUT") || method.equals("PATCH");
    }

    default ErrorMessage getErrorMessage(Response response) {
        try {
            return GSON.fromJson(response.body().string(), ErrorMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
