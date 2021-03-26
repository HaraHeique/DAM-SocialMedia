package com.example.socialmedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.ViewHolders.FriendViewHolder;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;

import java.util.ArrayList;
import java.util.Collection;
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
                        if (item.name.contains(charSequence) || item.login.contains(charSequence)) {
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
        this.usersCopy = new ArrayList<>(this.users);
        notifyDataSetChanged();
    }

    public void updateAllUsersList(List<User> allUsers) {
        this.usersAll = allUsers;
        notifyDataSetChanged();
    }
}
