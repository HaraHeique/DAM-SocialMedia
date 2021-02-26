package com.example.socialmedia.ViewModels.Factories;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.ViewModels.PostViewModel;

public class PostViewModelFactory implements ViewModelProvider.Factory {

    private Resources resources;

    public PostViewModelFactory(Resources resources) {
        this.resources = resources;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostViewModel(resources);
    }
}
