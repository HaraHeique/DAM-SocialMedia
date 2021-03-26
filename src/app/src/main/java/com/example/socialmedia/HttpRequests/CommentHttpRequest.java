package com.example.socialmedia.HttpRequests;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.Comment;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.Utils.DateTimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CommentHttpRequest {

    private static CommentHttpRequest instance;

    public static CommentHttpRequest getInstance() {
        instance = (instance == null) ? new CommentHttpRequest() : instance;
        return instance;
    }

    public ObjectResponse<List<Comment>> getComments(String postId) {
        String requestUrl = AppConfig.BASE_URL + "pegar_comentarios.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "GET", "UTF-8");
        httpRequest.addParam("idpost", postId);

        ObjectResponse<List<Comment>> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(getComments(objResponse, postId));
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    public ObjectResponse<Comment> addComment(Comment comment) {
        String requestUrl = AppConfig.BASE_URL + "comentar.php";

        HttpRequest httpRequest = new HttpRequest(requestUrl, "POST", "UTF-8");
        httpRequest.addParam("login", comment.user.login);
        httpRequest.addParam("auth_token", comment.user.authToken);
        httpRequest.addParam("comentario", comment.description);
        httpRequest.addParam("idpost", comment.post.id);

        ObjectResponse<Comment> objResponse;

        try {
            InputStream inputStream = httpRequest.execute();
            String response = httpRequest.getResponseString(inputStream, "UTF-8");
            httpRequest.finish();

            objResponse = httpRequest.getCommonObject(response);
            objResponse.setData(comment);
        } catch (IOException | JSONException e) {
            objResponse = new ObjectResponse<>(e);
        }

        return objResponse;
    }

    private List<Comment> getComments(ObjectResponse<List<Comment>> objResponse, String postId) throws JSONException {
        List<Comment> comments = new ArrayList<>();

        if (!objResponse.success) { return comments; }

        JSONArray jsonArray = objResponse.jsonObject.getJSONArray("comentarios");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Post post = new Post(postId);

            User user = new User(
                jsonObject.getString("nome"),
                jsonObject.getString("foto_usuario")
            );

            Comment comment = new Comment(
                jsonObject.getString("idcomentario"),
                jsonObject.getString("texto"),
                DateTimeUtil.convertToDate(jsonObject.getLong("data_hora")),
                user,
                post
            );

            comments.add(comment);
        }

        return comments;
    }
}
