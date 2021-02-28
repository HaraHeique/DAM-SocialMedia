package com.example.socialmedia.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.FriendAdapter;
import com.example.socialmedia.R;
import com.example.socialmedia.ViewModels.Factories.FriendViewModelFactory;
import com.example.socialmedia.ViewModels.FriendViewModel;

public class FriendActivity extends BaseActivity {

    private FriendViewModel friendViewModel;
    private FriendAdapter friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        setToolbarConfig(R.id.tb_friend, "Amigos", true);
        setupFriendViewModel();
        friendAdapter = new FriendAdapter(friendViewModel.getFollowingList(true), friendViewModel.getFriendsList());
        setupRecyclerFriendList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);
        MenuItem searchItem = menu.findItem(R.id.sv_search_friends);
        onSearchQueryEvent(searchItem);

        return true;
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

    private void setupFriendViewModel() {
        FriendViewModelFactory friendViewModelFactory = new FriendViewModelFactory(getResources());
        friendViewModel = new ViewModelProvider(this, friendViewModelFactory).get(FriendViewModel.class);
    }

    private void setupRecyclerFriendList() {
        RecyclerView postRecycleList = findViewById(R.id.rv_friends);
        postRecycleList.setAdapter(friendAdapter);
        postRecycleList.setLayoutManager(new LinearLayoutManager(context));
    }
}