package com.example.socialmedia.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.CommentAdapter;
import com.example.socialmedia.R;
import com.example.socialmedia.ViewModels.CommentViewModel;
import com.example.socialmedia.ViewModels.Factories.CommentViewModelFactory;

public class CommentActivity extends BaseActivity {

    private CommentViewModel commentViewModel;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        setToolbarConfig(R.id.tb_comment, "Comentários do Post", true);
        setupCommentViewModel();
        commentAdapter = new CommentAdapter(commentViewModel.getCommentsList());
        setupRecyclerCommentsList();
    }

    private void setToolbarConfig() {
        Intent intent = getIntent();
        String postId = intent.getStringExtra("postId");
    }

    private void setupCommentViewModel() {
        CommentViewModelFactory commentViewModelFactory = new CommentViewModelFactory(context);
        commentViewModel = new ViewModelProvider(this, commentViewModelFactory).get(CommentViewModel.class);
    }

    private void setupRecyclerCommentsList() {
        RecyclerView commentRecycleList = findViewById(R.id.rv_comments);
        commentRecycleList.setAdapter(commentAdapter);
        commentRecycleList.setLayoutManager(new LinearLayoutManager(context));
    }
}