package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.Enums.TimelineType;
import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.PostHttpRequest;
import com.example.socialmedia.HttpRequests.UserHttpRequest;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.Models.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostViewModel extends ViewModel {

    public TimelineType timelineType;
    public boolean pressFollowButton;

    private final CurrentUser currentUser;
    private final MutableLiveData<ObjectResponse<List<Post>>> postsList;
    private final MutableLiveData<ObjectResponse<User>> userFollowState;

    public PostViewModel(CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.postsList = new MutableLiveData<>();
        this.userFollowState = new MutableLiveData<>();
        this.timelineType = TimelineType.ALL_WORLD;
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<List<Post>>> observePostList() {
        return this.postsList;
    }

    public MutableLiveData<ObjectResponse<User>> observeUserFollowState() {
        return this.userFollowState;
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

    public void changeFollowState(User user, String who, boolean following) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            UserHttpRequest userRequest = UserHttpRequest.getInstance();
            ObjectResponse<User> objResponse;

            if (following) {
                objResponse = userRequest.follow(user, who);
            } else {
                objResponse = userRequest.unfollow(user, who);
            }

            this.pressFollowButton = following;
            this.userFollowState.postValue(objResponse);
        });
    }
}
