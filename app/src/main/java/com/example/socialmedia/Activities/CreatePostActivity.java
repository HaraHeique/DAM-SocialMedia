package com.example.socialmedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socialmedia.R;
import com.example.socialmedia.Utils.LayoutUtil;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        LayoutUtil.setTitleActionBar(getSupportActionBar(), "Novo Post");
    }
}