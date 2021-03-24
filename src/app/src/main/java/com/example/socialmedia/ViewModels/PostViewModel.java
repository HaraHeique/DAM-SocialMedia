package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.Enums.TimelineType;
import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.PostHttpRequest;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostViewModel extends ViewModel {

    private final CurrentUser currentUser;
    private final MutableLiveData<ObjectResponse<List<Post>>> postsList;

    public PostViewModel(CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.postsList = new MutableLiveData<>();
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<List<Post>>> observePostList() {
        return this.postsList;
    }

    // Requests
    public void getPostsList(TimelineType type) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            PostHttpRequest postRequest = PostHttpRequest.getInstance();
            ObjectResponse<List<Post>> objResponse = postRequest.getPosts(currentUser, type);
            this.postsList.postValue(objResponse);
        });
    }
}
