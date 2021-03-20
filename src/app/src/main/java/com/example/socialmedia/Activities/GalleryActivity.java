package com.example.socialmedia.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.GalleryAdapter;
import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.ViewModels.Factories.PostViewModelFactory;
import com.example.socialmedia.ViewModels.PostViewModel;

import java.util.List;

public class GalleryActivity extends BaseActivity {

    private PostViewModel postViewModel;
    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        setToolbarConfig(R.id.tb_gallery, "Galeria", true);
        setupPostViewModel();
        setupGalleryAdapter();
        setupRecyclerGalleryGrid();
    }

    public void startPostImageActivity(int position) {
        Intent intent = new Intent(context, PostImageActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void setupPostViewModel() {
        PostViewModelFactory postVMFactory = new PostViewModelFactory(context);
        postViewModel = new ViewModelProvider(this, postVMFactory).get(PostViewModel.class);
    }

    private void setupGalleryAdapter() {
        CurrentUser currentUser = AppConfig.getCurrentUser(context);
        List<Post> currentUserPosts = postViewModel.getImagePostsByLogin(currentUser.login);
        galleryAdapter = new GalleryAdapter(currentUserPosts);
    }

    private void setupRecyclerGalleryGrid() {
        RecyclerView galleryRecycleGrid = findViewById(R.id.rv_gallery);
        galleryRecycleGrid.setAdapter(galleryAdapter);

        // Definindo os espaçamento entre os itens do grid antes de definir o layout manager
        float width = getResources().getDimension(R.dimen.im_width);
        int numberOfColumns = ImageUtil.calculateNumberOfColumns(context, width);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, numberOfColumns);
        galleryRecycleGrid.setLayoutManager(gridLayoutManager);
    }
}