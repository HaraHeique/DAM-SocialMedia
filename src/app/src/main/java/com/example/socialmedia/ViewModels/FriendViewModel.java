package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.UserHttpRequest;
import com.example.socialmedia.Models.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FriendViewModel extends ViewModel {

    private final MutableLiveData<ObjectResponse<List<User>>> friendsList;
    private final MutableLiveData<ObjectResponse<List<User>>> allUsersList;

    public FriendViewModel() {
        this.friendsList = new MutableLiveData<>();
        this.allUsersList = new MutableLiveData<>();
    }

    // Observer (LiveData)
    public LiveData<ObjectResponse<List<User>>> observeAllUsersList() {
        return this.allUsersList;
    }

    public LiveData<ObjectResponse<List<User>>> observeFriendsList() {
        return this.friendsList;
    }

    // Requests
    public void getAllUsers() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            UserHttpRequest userRequest = UserHttpRequest.getInstance();
            ObjectResponse<List<User>> objResponse = userRequest.getAllUsers();
            this.allUsersList.postValue(objResponse);
        });
    }

    public void getFriendsList(User user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            UserHttpRequest userRequest = UserHttpRequest.getInstance();
            ObjectResponse<List<User>> objResponse = userRequest.getUsersFollowing(user);
            this.friendsList.postValue(objResponse);
        });
    }
}
