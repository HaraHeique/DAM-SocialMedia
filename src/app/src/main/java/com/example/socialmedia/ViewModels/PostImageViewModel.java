package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.PostHttpRequest;
import com.example.socialmedia.Models.Post;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostImageViewModel extends ViewModel {

    public String postId;

    private final MutableLiveData<ObjectResponse<Post>> postImage;

    public PostImageViewModel() {
        this.postImage = new MutableLiveData<>();
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<Post>> observePostImage() {
        return this.postImage;
    }

    // Requests
    public void getPost(String postId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            PostHttpRequest postRequest = PostHttpRequest.getInstance();
            ObjectResponse<Post> objResponse = postRequest.getPost(postId);
            this.postImage.postValue(objResponse);
        });
    }
}
