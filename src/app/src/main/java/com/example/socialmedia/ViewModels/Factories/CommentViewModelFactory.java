package com.example.socialmedia.ViewModels.Factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.ViewModels.CommentViewModel;

public class CommentViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public CommentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CommentViewModel(this.context);
    }
}
