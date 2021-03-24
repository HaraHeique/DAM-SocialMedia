package com.example.socialmedia.Adapters.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.ImageUtil;

public class GalleryViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageIv;

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);

        this.imageIv = itemView.findViewById(R.id.imv_gallery);
    }

    public void bind(Post post) {
        this.imageIv.setImageBitmap(ImageUtil.base64ToBitmap(post.image));
    }
}
