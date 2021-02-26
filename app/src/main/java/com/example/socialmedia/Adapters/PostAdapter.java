package com.example.socialmedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.ViewHolders.PostCommentViewHolder;
import com.example.socialmedia.Adapters.ViewHolders.PostImageViewHolder;
import com.example.socialmedia.Adapters.ViewHolders.PostViewHolder;
import com.example.socialmedia.Enums.PostType;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private final List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        PostType type = PostType.values()[viewType];

        if (type == PostType.IMAGE) {
            View itemView = inflater.inflate(R.layout.adapter_list_post_image, parent, false);
            return new PostImageViewHolder(itemView);
        } else {
            View itemView = inflater.inflate(R.layout.adapter_list_post_comment, parent, false);
            return new PostCommentViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return posts.get(position).type.ordinal();
    }
}
