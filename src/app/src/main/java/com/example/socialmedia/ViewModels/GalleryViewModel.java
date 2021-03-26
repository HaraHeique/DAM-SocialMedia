package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.PostHttpRequest;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<ObjectResponse<List<Post>>> postsImageList;

    public GalleryViewModel() {
        this.postsImageList = new MutableLiveData<>();
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<List<Post>>> observePostsImageList() {
        return this.postsImageList;
    }

    // Requests
    public void getPostsImage(CurrentUser currentUser) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            PostHttpRequest postRequest = PostHttpRequest.getInstance();
            ObjectResponse<List<Post>> objResponse = postRequest.getPostsImage(currentUser);
            this.postsImageList.postValue(objResponse);
        });
    }
}
