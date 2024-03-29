package com.example.socialmedia.HttpRequests;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Enums.TimelineType;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;
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

public final class PostHttpRequest {

    private static PostHttpRequest instance;

    public static PostHttpRequest getInstance() {
        instance = (instance == null) ? new PostHttpRequest() : instance;
        return instance;
    }

    public ObjectResponse<Post> getPost(String postId) {
        String requestUrl = AppConfig.BASE_URL + "pegar_post.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "GET", "UTF-8");
        httpRequest.addParam("idpost", postId);

        ObjectResponse<Post> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(getPost(objResponse));
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    public ObjectResponse<List<Post>> getPosts(CurrentUser currentUser, TimelineType type) {
        String requestUrl = AppConfig.BASE_URL + "pegar_posts.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", currentUser.login);
        httpRequest.addParam("auth_token", currentUser.authToken);
        httpRequest.addParam("tipo_timeline", Integer.toString(type.getValue()));

        ObjectResponse<List<Post>> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(getPosts(objResponse));
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    public ObjectResponse<List<Post>> getPostsImage(CurrentUser currentUser) {
        String requestUrl = AppConfig.BASE_URL + "pegar_galeria.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", currentUser.login);
        httpRequest.addParam("auth_token", currentUser.authToken);

        ObjectResponse<List<Post>> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(getPostsImage(objResponse));
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    public ObjectResponse<Post> createPost(Post post, File image) {
        String requestUrl = AppConfig.BASE_URL + "post.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", post.user.login);
        httpRequest.addParam("auth_token", post.user.authToken);
        httpRequest.addParam("texto", post.description);
        if (image != null) { httpRequest.addFile("foto", image); }

        ObjectResponse<Post> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(post);
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    private Post getPost(ObjectResponse<Post> objResponse) throws JSONException {
        if (!objResponse.success) { return null; }

        JSONArray jsonArray = objResponse.jsonObject.getJSONArray("post");

        if (jsonArray.length() == 0) { return null; }

        JSONObject jsonObject = jsonArray.getJSONObject(0);

        User user = new User(
            jsonObject.getString("nome"),
            "",
            jsonObject.getInt("seguindo") == 1,
            jsonObject.getString("foto_usuario")
        );

        Post post = new Post(
            jsonObject.getString("idpost"),
            jsonObject.getString("texto"),
            DateTimeUtil.convertToDate(jsonObject.getLong("data_hora")),
            jsonObject.getString("imagem"),
            user
        );

        return post;
    }

    private List<Post> getPosts(ObjectResponse<List<Post>> objResponse) throws JSONException {
        List<Post> posts = new ArrayList<>();

        if (!objResponse.success) { return posts; }

        JSONArray jsonArray = objResponse.jsonObject.getJSONArray("posts");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            User user = new User(
                jsonObject.getString("nome"),
                jsonObject.getString("login"),
                jsonObject.getInt("seguindo") == 1,
                jsonObject.getString("foto_usuario")
            );

            Post post = new Post(
                jsonObject.getString("idpost"),
                jsonObject.getString("texto"),
                DateTimeUtil.convertToDate(jsonObject.getLong("data_hora")),
                jsonObject.getString("imagem"),
                user
            );

            posts.add(post);
        }

        return posts;
    }

    private List<Post> getPostsImage(ObjectResponse<List<Post>> objResponse) throws JSONException {
        List<Post> posts = new ArrayList<>();

        if (!objResponse.success) { return posts; }

        JSONArray jsonArray = objResponse.jsonObject.getJSONArray("fotos");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Post post = new Post(
                jsonObject.getString("idpost"),
                jsonObject.getString("imagem")
            );

            posts.add(post);
        }

        return posts;
    }
}
