package com.example.socialmedia.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.socialmedia.Adapters.PostAdapter;
import com.example.socialmedia.Enums.PostType;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.Utils.LayoutUtil;
import com.example.socialmedia.Utils.LoginSessionUtil;
import com.example.socialmedia.ViewModels.Factories.PostViewModelFactory;
import com.example.socialmedia.ViewModels.PostViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private static final int CREATE_POST_RESULT = 1;
    private final Context CONTEXT = MainActivity.this;
    private Menu menu;
    private PostViewModel postViewModel;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutUtil.setTitleActionBar(getSupportActionBar(), "Timeline");
        setupPostViewModel();
        postAdapter = new PostAdapter(postViewModel.getPostsList());
        setupRecyclerPostList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setVisibleMenu();
    }

    private void logout() {
        LoginSessionUtil.setLogin(CONTEXT, false);
        Intent intent = new Intent(CONTEXT, LoginActivity.class);
        startActivity(intent);
    }

    private void onClickIconAddNewPost() {
        Intent intent = new Intent(CONTEXT, CreatePostActivity.class);
        startActivityForResult(intent, CREATE_POST_RESULT);
    }

    private void setupPostViewModel() {
        PostViewModelFactory postVMFactory = new PostViewModelFactory(getResources());
        postViewModel = new ViewModelProvider(this, postVMFactory).get(PostViewModel.class);
    }

    private void setupRecyclerPostList() {
        RecyclerView postRecycleList = findViewById(R.id.rv_posts);
        postRecycleList.setAdapter(postAdapter);
        postRecycleList.setLayoutManager(new LinearLayoutManager(CONTEXT));
        DividerItemDecoration decoration = new DividerItemDecoration(postRecycleList.getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ImageUtil.getDrawable(getResources(), R.drawable.shape_line_divider));
        postRecycleList.addItemDecoration(decoration);
    }

    private void setVisibleMenu() {
        if (menu == null) return;

        BottomNavigationView navigationView = findViewById(R.id.bnv_posts);
        Menu menuNav = navigationView.getMenu();

        // Menu inferior
        MenuItem allWorldMenuItem = menuNav.findItem(R.id.op_allworld);
        MenuItem myWorldMenuItem = menuNav.findItem(R.id.op_myworld);
        MenuItem onlyMeMenuItem = menuNav.findItem(R.id.op_onlyme);

        // Menu superior
        MenuItem loginMenuItem = menu.findItem(R.id.op_login);
        MenuItem logoutMenuItem = menu.findItem(R.id.op_logout);
        MenuItem addPostMenuItem = menu.findItem(R.id.op_addpost);

        if (LoginSessionUtil.isLogged(CONTEXT)) {
            LayoutUtil.showItemMenu(allWorldMenuItem);
            LayoutUtil.showItemMenu(myWorldMenuItem);
            LayoutUtil.showItemMenu(onlyMeMenuItem);

            LayoutUtil.hideItemMenu(loginMenuItem);
            LayoutUtil.showItemMenu(logoutMenuItem);
            LayoutUtil.showItemMenu(addPostMenuItem);
        } else {
            LayoutUtil.showItemMenu(allWorldMenuItem);
            LayoutUtil.hideItemMenu(myWorldMenuItem);
            LayoutUtil.hideItemMenu(onlyMeMenuItem);

            LayoutUtil.showItemMenu(loginMenuItem);
            LayoutUtil.hideItemMenu(logoutMenuItem);
            LayoutUtil.hideItemMenu(addPostMenuItem);
        }
    }

    private boolean checkPermissionInPage() {
        if (!LoginSessionUtil.isLogged(CONTEXT)) {
            logout();
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nav_top, menu);
        setVisibleMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.op_login || itemId == R.id.op_logout) {
            logout();
        }

        if (itemId == R.id.op_addpost) {
            onClickIconAddNewPost();
        }

        return super.onOptionsItemSelected(item);
    }
}