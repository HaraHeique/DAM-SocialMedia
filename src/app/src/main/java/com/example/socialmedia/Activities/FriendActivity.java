package com.example.socialmedia.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.FriendAdapter;
import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.ViewModels.FriendViewModel;

import java.util.ArrayList;

public class FriendActivity extends BaseActivity {

    private FriendViewModel friendViewModel;
    private FriendAdapter friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        setToolbarConfig(R.id.tb_friend, "Amigos", true);
        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        setupRecyclerFriendList();
        observeUsersFriendsList();
        observeAllUsersList();
        getUsers();
    }

    private void setupRecyclerFriendList() {
        friendAdapter = new FriendAdapter(new ArrayList<>(), new ArrayList<>());
        RecyclerView postRecycleList = findViewById(R.id.rv_friends);
        postRecycleList.setAdapter(friendAdapter);
        postRecycleList.setLayoutManager(new LinearLayoutManager(context));
    }

    private void observeUsersFriendsList() {
        friendViewModel.observeFriendsList().observe(this, objResponse -> {
            if (objResponse.success) {
                friendAdapter.updateUsersFriendsList(objResponse.data);
            } else {
                AlertMessageUtil.defaultAlert(context, objResponse.message);
            }
        });
    }

    private void observeAllUsersList() {
        friendViewModel.observeAllUsersList().observe(this, objResponse -> {
            if (objResponse.success) {
                objResponse.data.remove(new User(AppConfig.getCurrentUser(context)));
                friendAdapter.updateAllUsersList(objResponse.data);
            } else {
                AlertMessageUtil.defaultAlert(context, objResponse.message);
            }
        });
    }

    private void getUsers() {
        friendViewModel.getFriendsList(new User(AppConfig.getCurrentUser(context)));
        friendViewModel.getAllUsers();
    }

    private void onSearchQueryEvent(MenuItem searchItem) {
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);
        MenuItem searchItem = menu.findItem(R.id.sv_search_friends);
        onSearchQueryEvent(searchItem);

        // Mudança nas cores dos icones do search view
        setColorItemView(searchItem.getActionView(), Color.WHITE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        ImageView closeButtonImage = searchView.findViewById(R.id.search_close_btn);
        closeButtonImage.setImageResource(R.drawable.ic_baseline_close_24);

        return true;
    }
}