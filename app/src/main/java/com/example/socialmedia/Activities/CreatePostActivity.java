package com.example.socialmedia.Activities;

import android.content.Context;
import android.os.Bundle;

import com.example.socialmedia.R;

public class CreatePostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        setToolbarConfig(R.id.tb_create_post, "Novo Post", true);
    }
}