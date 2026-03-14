package utils;

import dto.Token;
import dto.User;
import okhttp3.Response;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;

public interface ILogin extends BaseApi{
    default Token loginGetToken() {
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        Response response = getResponse(LOGIN_URL, "POST", user, null);
        Token token;
        try {
            token = GSON.fromJson(response.body().string(), Token.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return token;
    }
}
