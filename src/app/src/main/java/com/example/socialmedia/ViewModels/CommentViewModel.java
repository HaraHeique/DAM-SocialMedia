package com.example.socialmedia.ViewModels;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.Comment;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentViewModel extends ViewModel {

    private final List<Comment> commentsList;

    public CommentViewModel(Context context) {
        this.commentsList = mockData(context);
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    private List<Comment> mockData(Context context) {
        Resources resources = context.getResources();
        CurrentUser currentUser = AppConfig.getCurrentUser(context);

        return new ArrayList<>();
    }
}
