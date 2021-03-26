package com.example.socialmedia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Activities.GalleryActivity;
import com.example.socialmedia.Adapters.ViewHolders.GalleryViewHolder;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

    private List<Post> posts;

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
        this.onClickPostImage(holder.itemView, post.id);
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public void updateGalleryList(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    private void onClickPostImage(View itemView, String postId) {
        ImageView imageView = itemView.findViewById(R.id.imv_gallery);
        GalleryActivity galleryActivity = (GalleryActivity) itemView.getContext();
        imageView.setOnClickListener(v -> galleryActivity.startPostImageActivity(postId));
    }
}
