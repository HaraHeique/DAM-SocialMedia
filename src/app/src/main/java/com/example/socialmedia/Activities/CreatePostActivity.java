package com.example.socialmedia.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;

public class CreatePostActivity extends BaseActivity {

    private static final int TAKE_PICTURE_RESULT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        setToolbarConfig(R.id.tb_create_post, "Novo Post", true);
        onClickPhotoPicker();
    }

    private void onClickPhotoPicker() {
        ImageView btnImg = findViewById(R.id.imv_createpost_image);

        btnImg.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, TAKE_PICTURE_RESULT);
        });
    }

    // Após ser executado o intent da câmera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE_RESULT && resultCode == RESULT_OK) {
            Bitmap imgBitmap = (Bitmap) data.getExtras().get("data");
            ImageView imageView = findViewById(R.id.imv_createpost_image);
            imageView.setImageBitmap(imgBitmap);
        } else {
            AlertMessageUtil.defaultAlert(context, "Não foi possível tirar a foto!");
        }
    }
}