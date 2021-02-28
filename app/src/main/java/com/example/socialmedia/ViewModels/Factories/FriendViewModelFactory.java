package com.example.socialmedia.ViewModels.Factories;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.ViewModels.FriendViewModel;

public class FriendViewModelFactory implements ViewModelProvider.Factory {

    private final Resources resources;

    public FriendViewModelFactory(Resources resources) {
        this.resources = resources;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FriendViewModel(this.resources);
    }
}
