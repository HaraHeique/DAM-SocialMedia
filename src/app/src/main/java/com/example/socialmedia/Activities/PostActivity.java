package com.example.socialmedia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.PostAdapter;
import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;
import com.example.socialmedia.ViewModels.Factories.PostViewModelFactory;
import com.example.socialmedia.ViewModels.PostViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PostActivity extends BaseActivity {

    private static final int CREATE_POST_RESULT = 1;

    private Menu menuTop;
    private PostViewModel postViewModel;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        setToolbarConfig(R.id.tb_main, "Timeline", false);
        //setupPostViewModel();
        //postAdapter = new PostAdapter(postViewModel.getPostsList());
        //setupRecyclerPostList();
        //onClickIconsMenuBottom();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //filterPostsByCurrentUser();
        //setVisibilityItemsMenu();
    }

    public void startCommentActivity(Post post) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("title", post.title);
        startActivity(intent);
    }

    private void logout() {
        AppConfig.setLogin(context, false);
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void onClickIconAddNewPost() {
        Intent intent = new Intent(context, CreatePostActivity.class);
        startActivityForResult(intent, CREATE_POST_RESULT);
    }

    private void onClickIconFriends() {
        Intent intent = new Intent(context, FriendActivity.class);
        startActivity(intent);
    }

    private void onClickIconGallery() {
        Intent intent = new Intent(context, GalleryActivity.class);
        startActivity(intent);
    }

    private void onClickIconsMenuBottom() {
        BottomNavigationView btvPosts = findViewById(R.id.bnv_posts);

        btvPosts.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.op_allworld) {
                List<Post> allWordList = postViewModel.getPostsList();
                postAdapter.updatePostList(allWordList);
                return true;
            }

            if (itemId == R.id.op_myworld) {
                List<Post> myWordList = postViewModel.getPostsByFollow(true);
                postAdapter.updatePostList(myWordList);
                return true;
            }

            if (itemId == R.id.op_onlyme) {
                CurrentUser currentUser = AppConfig.getCurrentUser(context);
                List<Post> onlyMeList = postViewModel.getPostsByLogin(currentUser.login);
                postAdapter.updatePostList(onlyMeList);
                return true;
            }

            return false;
        });
    }

    private void setupPostViewModel() {
        PostViewModelFactory postVMFactory = new PostViewModelFactory(context);
        postViewModel = new ViewModelProvider(this, postVMFactory).get(PostViewModel.class);
    }

    private void setupRecyclerPostList() {
        RecyclerView postRecycleList = findViewById(R.id.rv_posts);
        postRecycleList.setAdapter(postAdapter);
        postRecycleList.setLayoutManager(new LinearLayoutManager(context));
    }

    private void setVisibilityItemsMenu() {
        if (menuTop == null) return;

        BottomNavigationView navigationView = findViewById(R.id.bnv_posts);
        Menu menuNav = navigationView.getMenu();

        // Menu inferior
        MenuItem allWorldMenuItem = menuNav.findItem(R.id.op_allworld);
        MenuItem myWorldMenuItem = menuNav.findItem(R.id.op_myworld);
        MenuItem onlyMeMenuItem = menuNav.findItem(R.id.op_onlyme);

        // Menu superior
        MenuItem loginMenuItem = menuTop.findItem(R.id.op_login);
        MenuItem logoutMenuItem = menuTop.findItem(R.id.op_logout);
        MenuItem addPostMenuItem = menuTop.findItem(R.id.op_addpost);
        MenuItem friendsMenuItem = menuTop.findItem(R.id.op_friends);
        MenuItem galleryMenuItem = menuTop.findItem(R.id.op_gallery);

        if (AppConfig.isLogged(context)) {
            showItemMenu(allWorldMenuItem);
            showItemMenu(myWorldMenuItem);
            showItemMenu(onlyMeMenuItem);

            hideItemMenu(loginMenuItem);
            showItemMenu(logoutMenuItem);
            showItemMenu(addPostMenuItem);
            showItemMenu(friendsMenuItem);
            showItemMenu(galleryMenuItem);
        } else {
            disableItemMenu(allWorldMenuItem);
            hideItemMenu(myWorldMenuItem);
            hideItemMenu(onlyMeMenuItem);

            showItemMenu(loginMenuItem);
            hideItemMenu(logoutMenuItem);
            hideItemMenu(addPostMenuItem);
            hideItemMenu(friendsMenuItem);
            hideItemMenu(galleryMenuItem);
        }
    }

    private void filterPostsByCurrentUser() {
        CurrentUser currentUser = AppConfig.getCurrentUser(context);

        if (!currentUser.isLogged) {
            postAdapter.updatePostList(postViewModel.getPostsNotLogin(currentUser.login));
        } else {
            postAdapter.updatePostList(postViewModel.getPostsList());
        }
    }

    private boolean checkPermissionInPage() {
        if (!AppConfig.isLogged(context)) {
            logout();
            return false;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menuTop = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nav_top, menu);
        setVisibilityItemsMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.op_login || itemId == R.id.op_logout) {
            logout();
            return false;
        }

        if (itemId == R.id.op_addpost) {
            onClickIconAddNewPost();
            return false;
        }

        if (itemId == R.id.op_friends) {
            onClickIconFriends();
            return false;
        }

        if (itemId == R.id.op_gallery) {
            onClickIconGallery();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}