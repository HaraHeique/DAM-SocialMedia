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
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.ViewModels.Factories.PostViewModelFactory;
import com.example.socialmedia.ViewModels.PostViewModel;

public class PostImageActivity extends BaseActivity {

    private PostViewModel postViewModel;
    private CurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);

        currentUser = AppConfig.getCurrentUser(context);
        setupPostViewModel();
        Post postSelected = getPostSelected();
        setToolbarConfig(R.id.tb_post_image, "Detalhes do Post", true);
        bindInfoPostImage(postSelected);
        onClickIconComment(postSelected);
    }

    private void setupPostViewModel() {
        CurrentUser currentUser = AppConfig.getCurrentUser(context);
        PostViewModelFactory postVMFactory = new PostViewModelFactory(currentUser);
        postViewModel = new ViewModelProvider(this, postVMFactory).get(PostViewModel.class);
    }

    private Post getPostSelected() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        //return postViewModel.getImagePostsByLogin(currentUser.login).get(position);
        return null;
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
        followingIv.setVisibility(View.GONE);
    }

    private void onClickIconComment(Post postSelected) {
        ImageView commentIv = findViewById(R.id.imv_timeline_comment);

        commentIv.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentActivity.class);
            startActivity(intent);
        });
    }
}