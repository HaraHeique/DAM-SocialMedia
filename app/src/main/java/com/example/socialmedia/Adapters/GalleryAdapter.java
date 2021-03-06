package com.example.socialmedia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Activities.GalleryActivity;
import com.example.socialmedia.Activities.MainActivity;
import com.example.socialmedia.Adapters.ViewHolders.GalleryViewHolder;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    private final List<Post> posts;

    public GalleryAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_grid_gallery, parent, false);

        return new GalleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Post post = this.posts.get(position);
        holder.bind(post);
        this.onClickPostImage(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    private void onClickPostImage(View itemView, int position) {
        ImageView imageView = itemView.findViewById(R.id.imv_gallery);
        imageView.setOnClickListener(v -> ((GalleryActivity)v.getContext()).startPostImageActivity(position));
    }
}
