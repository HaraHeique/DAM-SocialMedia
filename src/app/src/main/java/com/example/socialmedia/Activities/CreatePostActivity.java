package com.example.socialmedia.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

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
        onClickPhotoPicker();
        createPostViewModel = new ViewModelProvider(this).get(CreatePostViewModel.class);
    }

    private void onClickPhotoPicker() {
        ImageView btnImg = findViewById(R.id.imv_createpost_image);

        btnImg.setOnClickListener(v -> {
            createPostViewModel.postImagePath = takePictureIntent(TAKE_PICTURE_RESULT);
        });
    }

    // Após ser executado o intent da câmera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE_RESULT && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.imv_createpost_image);
            Bitmap imgBitmap = ImageUtil.getBitmap(createPostViewModel.postImagePath);
            imageView.setImageBitmap(ImageUtil.autoRotateImage(imgBitmap, createPostViewModel.postImagePath));
        } else {
            new File(createPostViewModel.postImagePath).delete();
            AlertMessageUtil.defaultAlert(context, "Não foi possível tirar a foto do post!");
        }
    }
}