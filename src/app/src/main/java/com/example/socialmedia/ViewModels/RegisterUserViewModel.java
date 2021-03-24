package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.HttpRequests.UserHttpRequest;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.Utils.ImageUtil;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterUserViewModel extends ViewModel {

    public String avatarImagePath;

    private final MutableLiveData<ObjectResponse<User>> userRegistered;

    public RegisterUserViewModel() {
        this.userRegistered = new MutableLiveData<>();
        this.avatarImagePath = "";
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<User>> observeUserRegistered() {
        return this.userRegistered;
    }

    // Requests
    public void registerUser(User user) {
        // Reduzir a escala da imagem para evitar timeout na request
        File avatar = ImageUtil.getImageFile(this.avatarImagePath, 100, 100);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            UserHttpRequest userRequest = UserHttpRequest.getInstance();
            ObjectResponse<User> objResponse = userRequest.register(user, avatar);
            this.userRegistered.postValue(objResponse);
        });
    }
}
