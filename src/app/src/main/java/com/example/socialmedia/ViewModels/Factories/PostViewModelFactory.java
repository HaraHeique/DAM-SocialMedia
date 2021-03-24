package com.example.socialmedia.ViewModels.Factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.ViewModels.PostViewModel;

public class PostViewModelFactory implements ViewModelProvider.Factory {

    private final CurrentUser currentUser;

    public PostViewModelFactory(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostViewModel(this.currentUser);
    }
}
