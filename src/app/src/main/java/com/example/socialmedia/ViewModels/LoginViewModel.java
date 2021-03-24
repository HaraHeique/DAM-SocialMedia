package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.UserHttpRequest;
import com.example.socialmedia.Models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<ObjectResponse<User>> userLogin;

    public LoginViewModel() {
        this.userLogin = new MutableLiveData<>();
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<User>> observeLogin() {
        return this.userLogin;
    }

    // Requests
    public void login(String login, String password) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            UserHttpRequest userRequest = UserHttpRequest.getInstance();
            ObjectResponse<User> objResponse = userRequest.login(login, password);
            this.userLogin.postValue(objResponse);
        });
    }
}
