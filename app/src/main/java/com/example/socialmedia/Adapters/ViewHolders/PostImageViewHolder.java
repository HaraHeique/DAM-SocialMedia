package com.example.socialmedia.Adapters.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;

public class PostImageViewHolder extends PostViewHolder {
    private final ImageView postImageIv;

    public PostImageViewHolder(@NonNull View itemView) {
        super(itemView);

        this.postImageIv = itemView.findViewById(R.id.imv_timeline_post);
    }

    @Override
    public void bind(Post post) {
        super.bind(post);

        this.postImageIv.setImageDrawable(post.image);
    }
}
