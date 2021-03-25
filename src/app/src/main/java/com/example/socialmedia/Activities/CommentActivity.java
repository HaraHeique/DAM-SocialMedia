package com.example.socialmedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.CommentAdapter;
import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.Comment;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.ViewModels.CommentViewModel;

import java.util.ArrayList;

public class CommentActivity extends BaseActivity {

    private CommentViewModel commentViewModel;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        setToolbarConfig(R.id.tb_comment, "Comentários do Post", true);
        setupCommentViewModel();
        setupRecyclerCommentsList();
        onClickBtnSendComment();
        observePostCommentList();
        observeAddComment();
        commentViewModel.getComments(commentViewModel.postId);
    }

    private void setupCommentViewModel() {
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        Intent intent = getIntent();
        commentViewModel.postId = intent.getStringExtra("postId");
    }

    private void setupRecyclerCommentsList() {
        commentAdapter = new CommentAdapter(new ArrayList<>());
        RecyclerView commentRecycleList = findViewById(R.id.rv_comments);
        commentRecycleList.setAdapter(commentAdapter);
        commentRecycleList.setLayoutManager(new LinearLayoutManager(context));
    }

    private void onClickBtnSendComment() {
        ImageButton btnSendComment = findViewById(R.id.imb_comment_send);

        btnSendComment.setOnClickListener(v -> {
            Comment comment = new Comment(
                ((EditText)findViewById(R.id.et_comment_send)).getText().toString(),
                new User(AppConfig.getCurrentUser(context)),
                new Post(commentViewModel.postId)
            );

            if (!ValidateAddComment(comment)) { return; }

            commentViewModel.addComment(comment);
        });
    }

    private void observePostCommentList() {
        commentViewModel.observePostCommentsList().observe(this, objResponse -> {
            if (objResponse.success) {
                commentAdapter.updateCommentList(objResponse.data);
            } else {
                AlertMessageUtil.defaultAlert(context, objResponse.message);
            }
        });
    }

    private void observeAddComment() {
        commentViewModel.observeAddComment().observe(this, objResponse -> {
            if (objResponse.success) {
                commentViewModel.getComments(objResponse.data.post.id);
                AlertMessageUtil.defaultAlert(context, "Comentário adicionado com sucesso.");
                ((EditText)findViewById(R.id.et_comment_send)).getText().clear();
            } else {
                AlertMessageUtil.defaultAlert(context, objResponse.message);
            }
        });
    }

    // Validações de entrada do usuário
    private boolean ValidateAddComment(Comment comment) {
        if (comment.description.length() < 2) {
            AlertMessageUtil.defaultAlert(context, "O comentário deve conter no mínimo 2 caracteres.");
            return false;
        }

        return ValidateUserComment(comment.user);
    }

    private boolean ValidateUserComment(User user) {
        if (user.login.isEmpty() || user.authToken.isEmpty()) {
            AlertMessageUtil.defaultAlert(context, "Usuário do post é inválido.");
            return false;
        }

        return true;
    }
}