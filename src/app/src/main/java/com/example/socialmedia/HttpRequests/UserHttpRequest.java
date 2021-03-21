package com.example.socialmedia.HttpRequests;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class UserHttpRequest {

    private HttpRequest httpRequest;

    public JSONObject register(User user) throws IOException, JSONException {
        String requestUrl = AppConfig.BASE_URL + "cadastra_usuario.php";

        httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", user.login);
        httpRequest.addParam("senha", user.password);
        httpRequest.addParam("nome", user.name);
        httpRequest.addParam("cidade", user.city);
        httpRequest.addParam("data_nascimento", Long.toString(DateTimeUtil.ConvertToUnixTimeStamp(user.bornDate)));
        httpRequest.addFile("foto", ImageUtil.getImageFile(user.avatarPath, 100, 100));

        InputStream inputStream = httpRequest.execute();
        String response = httpRequest.getResponseString(inputStream, "UTF-8");
        httpRequest.finish();

        return new JSONObject(response);
    }

    public JSONObject login(String login, String password) throws IOException, JSONException {
        String requestUrl = AppConfig.BASE_URL + "login.php";

        httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", login);
        httpRequest.addParam("senha", password);
        httpRequest.addParam("app_token", "");

        InputStream inputStream = httpRequest.execute();
        String response = httpRequest.getResponseString(inputStream, "UTF-8");
        httpRequest.finish();

        return new JSONObject(response);
    }
}
