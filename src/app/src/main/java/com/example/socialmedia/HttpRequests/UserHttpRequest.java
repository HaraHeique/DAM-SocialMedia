package com.example.socialmedia.HttpRequests;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.Utils.DateTimeUtil;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class UserHttpRequest {

    private static UserHttpRequest instance;

    public static UserHttpRequest getInstance() {
        instance = (instance == null) ? new UserHttpRequest() : instance;
        return instance;
    }

    public ObjectResponse<User> register(User user, File avatarImg) {
        String requestUrl = AppConfig.BASE_URL + "cadastra_usuario.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", user.login);
        httpRequest.addParam("senha", user.password);
        httpRequest.addParam("nome", user.name);
        httpRequest.addParam("cidade", user.city);
        httpRequest.addParam("data_nascimento", Long.toString(DateTimeUtil.ConvertToUnixTimeStamp(user.bornDate)));
        httpRequest.addFile("foto", avatarImg);

        ObjectResponse<User> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(user);
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    public ObjectResponse<User> login(String login, String password) {
        String requestUrl = AppConfig.BASE_URL + "login.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", login);
        httpRequest.addParam("senha", password);
        httpRequest.addParam("app_token", "");

        ObjectResponse<User> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(getUserLogin(objResponse, login, password));
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    public User getUserLogin(ObjectResponse<User> objResponse, String login, String password) throws JSONException {
        if (!objResponse.success) { return null; }

        String authToken = objResponse.jsonObject.getString("auth_token");

        return new User(login, password, authToken);
    }
}
