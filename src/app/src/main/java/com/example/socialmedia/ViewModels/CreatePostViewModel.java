package com.example.socialmedia.ViewModels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.PostHttpRequest;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.Utils.ImageUtil;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreatePostViewModel extends ViewModel {

    public String postImagePath;

    private final MutableLiveData<ObjectResponse<Post>> createdPost;

    public CreatePostViewModel() {
        this.createdPost = new MutableLiveData<>();
        this.postImagePath = "";
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<Post>> observeCreatedPost() {
        return this.createdPost;
    }

    // Requests
    public void createPost(Post post) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            File postImage = null;

            if (!this.postImagePath.isEmpty()) {
                // Reduzir a escala da imagem para evitar timeout na request
                Bitmap imgBitmap = ImageUtil.getBitmap(this.postImagePath, 300, 300, true);
                postImage = ImageUtil.getImageFile(this.postImagePath, imgBitmap);
            }

            PostHttpRequest postRequest = PostHttpRequest.getInstance();
            ObjectResponse<Post> objResponse = postRequest.createPost(post, postImage);
            this.createdPost.postValue(objResponse);
        });
    }
}
