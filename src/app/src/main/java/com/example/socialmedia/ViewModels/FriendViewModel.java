package com.example.socialmedia.ViewModels;

import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.example.socialmedia.Models.User;

import java.util.ArrayList;
import java.util.List;

public class FriendViewModel extends ViewModel {

    private final List<User> usersList;

    public FriendViewModel(Resources resources) {
        this.usersList = mockData(resources);
    }

    public List<User> getFriendsList() {
        return this.usersList;
    }

    public List<User> getFollowingList(boolean following) {
        // Queria utilizar stream(), mas não é possível para API < 24
        List<User> result = new ArrayList<>();

        for (User user: usersList) {
            if (user.following == following) {
                result.add(user);
            }
        }

        return result;
    }

    private List<User> mockData(Resources resources) {
        return new ArrayList<>();
    }
}
