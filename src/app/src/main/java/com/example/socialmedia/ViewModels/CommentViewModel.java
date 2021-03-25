package com.example.socialmedia.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialmedia.HttpRequests.CommentHttpRequest;
import com.example.socialmedia.HttpRequests.ObjectResponse;
import com.example.socialmedia.Models.Comment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentViewModel extends ViewModel {

    public String postId;

    private final MutableLiveData<ObjectResponse<List<Comment>>> postCommentsList;
    private final MutableLiveData<ObjectResponse<Comment>> addComment;

    public CommentViewModel() {
        this.postCommentsList = new MutableLiveData<>();
        this.addComment = new MutableLiveData<>();
        this.postId = "";
    }

    // Observes (LiveData)
    public LiveData<ObjectResponse<List<Comment>>> observePostCommentsList() {
        return this.postCommentsList;
    }

    public LiveData<ObjectResponse<Comment>> observeAddComment() {
        return this.addComment;
    }

    // Requests
    public void getComments(String postId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            CommentHttpRequest commentRequest = CommentHttpRequest.getInstance();
            ObjectResponse<List<Comment>> objResponse = commentRequest.getComments(postId);
            this.postCommentsList.postValue(objResponse);
        });
    }

    public void addComment(Comment comment) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            CommentHttpRequest commentRequest = CommentHttpRequest.getInstance();
            ObjectResponse<Comment> objResponse = commentRequest.addComment(comment);
            this.addComment.postValue(objResponse);
        });
    }
}
