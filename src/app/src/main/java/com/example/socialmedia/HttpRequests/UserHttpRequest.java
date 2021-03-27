package com.example.socialmedia.HttpRequests;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.Utils.DateTimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public ObjectResponse<User> login(String login, String password, String appToken) {
        String requestUrl = AppConfig.BASE_URL + "login.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", login);
        httpRequest.addParam("senha", password);
        httpRequest.addParam("app_token", appToken);

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

    public ObjectResponse<User> follow(User user, String whoToFollow) {
        return sendRequestFollowChangeState(user, whoToFollow, true);
    }

    public ObjectResponse<User> unfollow(User user, String whoToUnfollow) {
        return sendRequestFollowChangeState(user, whoToUnfollow, false);
    }

    public ObjectResponse<List<User>> getUsersFollowing(User user) {
        String requestUrl = AppConfig.BASE_URL + "pegar_seguindo.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", user.login);
        httpRequest.addParam("auth_token", user.authToken);

        ObjectResponse<List<User>> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(getUserFollowing(objResponse));
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    public ObjectResponse<List<User>> getAllUsers() {
        String requestUrl = AppConfig.BASE_URL + "buscar_usuario.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "GET", "UTF-8");
        httpRequest.addParam("login", "");
        httpRequest.addParam("busca", "");

        ObjectResponse<List<User>> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(getAllUsers(objResponse));
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    private ObjectResponse<User> sendRequestFollowChangeState(User user, String who, boolean following) {
        String baseUrl = AppConfig.BASE_URL;
        String requestUrl = following ? baseUrl + "seguir.php" : baseUrl + "desfazer_seguir.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", user.login);
        httpRequest.addParam("auth_token", user.authToken);
        httpRequest.addParam("quem", who);

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

    private User getUserLogin(ObjectResponse<User> objResponse, String login, String password) throws JSONException {
        if (!objResponse.success) { return null; }

        String authToken = objResponse.jsonObject.getString("auth_token");

        return new User(login, password, authToken);
    }

    private List<User> getUserFollowing(ObjectResponse<List<User>> objResponse) throws JSONException {
        List<User> users = new ArrayList<>();

        if (!objResponse.success) { return users; }

        JSONArray jsonArray = objResponse.jsonObject.getJSONArray("seguindo");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            User user = new User(
                jsonObject.getString("login"),
                jsonObject.getString("nome"),
                DateTimeUtil.convertToDate(jsonObject.getLong("data_nascimento")),
                jsonObject.getString("cidade"),
                true,
                jsonObject.getString("foto")
            );

            users.add(user);
        }

        return users;
    }

    private List<User> getAllUsers(ObjectResponse<List<User>> objResponse) throws JSONException {
        List<User> users = new ArrayList<>();

        if (!objResponse.success) { return users; }

        JSONArray jsonArray = objResponse.jsonObject.getJSONArray("usuarios");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject bornDateObject = jsonObject.getJSONObject("data_nascimento");

            User user = new User(
                jsonObject.getString("login"),
                jsonObject.getString("nome"),
                DateTimeUtil.convertToDateTime(bornDateObject.getString("date"), "yyyy-MM-dd HH:mm:ss.SSSSSS"),
                jsonObject.getString("cidade"),
                jsonObject.getInt("seguindo") == 1,
                jsonObject.getString("foto")
            );

            users.add(user);
        }

        return users;
    }
}
