package com.example.socialmedia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.ViewModels.CreatePostViewModel;

import java.io.File;

public class CreatePostActivity extends BaseActivity {

    private static final int TAKE_PICTURE_RESULT = 100;

    private CreatePostViewModel createPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        setToolbarConfig(R.id.tb_create_post, "Novo Post", true);
        createPostViewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);
        onClickPhotoPicker();
        onClickBtnCreatePost();
        observeCreatePost();
    }

    private void onClickPhotoPicker() {
        ImageView btnImg = findViewById(R.id.imv_createpost_image);

        btnImg.setOnClickListener(v -> {
            createPostViewModel.postImagePath = takePictureIntent(TAKE_PICTURE_RESULT);
        });
    }

    private void onClickBtnCreatePost() {
        Button btnCreatePost = findViewById(R.id.btn_createpost_confirm);

        btnCreatePost.setOnClickListener(v -> {
            Post post = new Post(
                ((EditText)findViewById(R.id.et_createpost_description)).getText().toString(),
                new User(AppConfig.getCurrentUser(context))
            );

            if (!ValidateCreatePost(post)) { return; }

            createPostViewModel.createPost(post);
        });
    }

    private void observeCreatePost() {
        createPostViewModel.observeCreatedPost().observe(this, objResponse -> {
            if (objResponse.success) {
                AlertMessageUtil.defaultAlert(context, "Post criado com sucesso.");
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                AlertMessageUtil.defaultAlert(context, objResponse.message);
            }
        });
    }

    // Após ser executado o intent da câmera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE_RESULT && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.imv_createpost_image);
            Bitmap imgBitmap = ImageUtil.getBitmap(createPostViewModel.postImagePath, true);
            imageView.setImageBitmap(imgBitmap);
        } else {
            new File(createPostViewModel.postImagePath).delete();
            createPostViewModel.postImagePath = "";
        }
    }

    // Validações do form de cadastro
    private boolean ValidateCreatePost(Post post) {
        if (post.description.length() < 2) {
            AlertMessageUtil.defaultAlert(context, "A descrição deve conter no mínimo 2 caracteres.");
            return false;
        }

        return ValidateUserPost(post.user);
    }

    private boolean ValidateUserPost(User user) {
        if (user.login.isEmpty() || user.authToken.isEmpty()) {
            AlertMessageUtil.defaultAlert(context, "Usuário do post é inválido.");
            return false;
        }

        return true;
    }
}