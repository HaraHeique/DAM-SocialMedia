package com.example.socialmedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Activities.FriendActivity;
import com.example.socialmedia.Adapters.ViewHolders.FriendViewHolder;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> implements Filterable {

    private List<User> users;
    private List<User> usersCopy;
    private List<User> usersAll;

    public FriendAdapter(List<User> usersFriends, List<User> allUsers) {
        this.users = usersFriends;
        this.usersCopy = new ArrayList<>(this.users);
        this.usersAll = allUsers;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.adapter_list_user, parent, false);

        return new FriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        User user = this.users.get(position);
        holder.bind(user);
        this.onClickBtnIconFollow(holder.itemView, user.login);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                String charSequence = constraint.toString().toLowerCase();

                if (charSequence.isEmpty()) {
                    filterResults.values = usersCopy;
                } else {
                    List<User> filteredList = new ArrayList<>();

                    for (int i = 0; i < usersAll.size(); i++) {
                        User item = usersAll.get(i);
                        String nameCompare = item.name.toLowerCase();
                        String loginCompare = item.login.toLowerCase();

                        if (nameCompare.contains(charSequence) || loginCompare.contains(charSequence)) {
                            filteredList.add(item);
                        }
                    }

                    filterResults.values = filteredList;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                users.clear();
                users.addAll((Collection<? extends User>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public void updateUsersFriendsList(List<User> usersFriends) {
        this.users = usersFriends;
        notifyUsersListChanged(this.users);
        this.usersCopy = new ArrayList<>(this.users);
    }

    public void updateAllUsersList(List<User> allUsers) {
        this.usersAll = allUsers;
        notifyUsersListChanged(allUsers);
    }

    public void notifyUsersListChanged(List<User> users) {
        Collections.sort(users);
        notifyDataSetChanged();
    }

    private void onClickBtnIconFollow(View itemView, String who) {
        ImageView btnFollow = itemView.findViewById(R.id.imv_user_follow);
        boolean following = (Boolean)btnFollow.getTag();
        btnFollow.setOnClickListener(v -> ((FriendActivity)v.getContext()).changeStateFollow(who, following));
    }
}
