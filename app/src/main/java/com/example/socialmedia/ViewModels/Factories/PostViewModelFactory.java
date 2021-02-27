package com.example.socialmedia.ViewModels.Factories;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.ViewModels.PostViewModel;

public class PostViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public PostViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PostViewModel(this.context);
    }
}
