package com.example.socialmedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.ViewModels.PostImageViewModel;

public class PostImageActivity extends BaseActivity {

    private PostImageViewModel postImageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);

        setToolbarConfig(R.id.tb_post_image, "Detalhes do Post", true);
        setupPostImageViewModel();
        onClickIconComment();
        observePostsImageList();
        postImageViewModel.getPost(postImageViewModel.postId);
    }

    private void setupPostImageViewModel() {
        postImageViewModel = new ViewModelProvider(this).get(PostImageViewModel.class);

        Intent intent = getIntent();
        postImageViewModel.postId = intent.getStringExtra("postId");
    }

    private void onClickIconComment() {
        ImageView commentIv = findViewById(R.id.imv_timeline_comment);

        commentIv.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            intent.putExtra("postId", postImageViewModel.postId);
            startActivity(intent);
        });
    }

    private void observePostsImageList() {
        postImageViewModel.observePostImage().observe(this, objResponse -> {
            if (objResponse.success) {
                CurrentUser currentUser = AppConfig.getCurrentUser(context);
                objResponse.data.user.login = currentUser.login;
                bindInfoPostImage(objResponse.data);
            } else {
                AlertMessageUtil.defaultAlert(context, objResponse.message);
            }
        });
    }

    private void bindInfoPostImage(Post postSelected) {
        TextView descriptionTv = findViewById(R.id.tv_timeline_description);
        TextView dateCreatedTv = findViewById(R.id.tv_timeline_data);
        TextView userNameTv = findViewById(R.id.tv_timeline_name);
        TextView userLoginTv = findViewById(R.id.tv_timeline_login);
        ImageView userAvatarTv = findViewById(R.id.imv_timeline_avatar);
        ImageView postImageIv = findViewById(R.id.imv_timeline_post);
        ImageView followingIv = findViewById(R.id.imv_timeline_follow);

        postImageIv.setImageBitmap(ImageUtil.base64ToBitmap(postSelected.image));
        descriptionTv.setText(postSelected.description);
        dateCreatedTv.setText(DateTimeUtil.convertToStrDateTime(postSelected.createDate));
        userNameTv.setText(postSelected.user.name);
        userLoginTv.setText(postSelected.user.login);
        userAvatarTv.setImageBitmap(ImageUtil.base64ToBitmap(postSelected.user.avatar));

        // Todos os posts são do usuário corrente, logo não precisa de mostrar o ícone de follow
        followingIv.setVisibility(View.GONE);
    }
}