package com.example.socialmedia.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.GalleryAdapter;
import com.example.socialmedia.AppConfig;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.ViewModels.GalleryViewModel;

import java.util.ArrayList;

public class GalleryActivity extends BaseActivity {

    private GalleryViewModel galleryViewModel;
    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        setToolbarConfig(R.id.tb_gallery, "Galeria", true);
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        setupRecyclerGalleryGrid();
        observePostsImageList();
        galleryViewModel.getPostsImage(AppConfig.getCurrentUser(context));
    }

    public void startPostImageActivity(String postId) {
        Intent intent = new Intent(context, PostImageActivity.class);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }

    private void setupRecyclerGalleryGrid() {
        RecyclerView galleryRecycleGrid = findViewById(R.id.rv_gallery);

        galleryAdapter = new GalleryAdapter(new ArrayList<>());
        galleryRecycleGrid.setAdapter(galleryAdapter);

        // Definindo os espaçamento entre os itens do grid antes de definir o layout manager
        float width = getResources().getDimension(R.dimen.im_width);
        int numberOfColumns = ImageUtil.calculateNumberOfColumns(context, width);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, numberOfColumns);
        galleryRecycleGrid.setLayoutManager(gridLayoutManager);
    }

    private void observePostsImageList() {
        galleryViewModel.observePostsImageList().observe(this, objResponse -> {
            if (objResponse.success) {
                galleryAdapter.updateGalleryList(objResponse.data);
            } else {
                AlertMessageUtil.defaultAlert(context, objResponse.message);
            }
        });
    }
}