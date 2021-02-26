package com.example.socialmedia.Adapters.ViewHolders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

public abstract class PostViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleTv;
    private final TextView descriptionTv;
    private final TextView dateCreatedTv;
    private final TextView userNameTv;
    private final TextView userLoginTv;
    private final ImageView userAvatarTv;
    private final ImageView followingIv;

    private final Resources resources;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        this.titleTv = itemView.findViewById(R.id.tv_timeline_title);
        this.descriptionTv = itemView.findViewById(R.id.tv_timeline_description);
        this.dateCreatedTv = itemView.findViewById(R.id.tv_timeline_data);
        this.userNameTv = itemView.findViewById(R.id.tv_timeline_name);
        this.userLoginTv = itemView.findViewById(R.id.tv_timeline_login);
        this.userAvatarTv = itemView.findViewById(R.id.imv_timeline_avatar);
        this.followingIv = itemView.findViewById(R.id.imv_timeline_follow);

        this.resources = itemView.getResources();
    }

    public void bind(Post post) {
        String formatDate = DateTimeUtil.ConvertToStrDateTime(post.createDate);
        Drawable followingIcon = this.getFollowingIcon(post.user.following);

        this.titleTv.setText(post.title);
        this.descriptionTv.setText(post.description);
        this.dateCreatedTv.setText(formatDate);
        this.userNameTv.setText(post.user.name);
        this.userLoginTv.setText(post.user.login);
        this.userAvatarTv.setImageDrawable(post.user.avatar);
        this.followingIv.setImageDrawable(followingIcon);
    }

    private Drawable getFollowingIcon(boolean following) {
        if (!following) {
            return ImageUtil.getDrawable(this.resources, R.drawable.ic_baseline_person_add_24);
        }

        return ImageUtil.getDrawable(this.resources, R.drawable.ic_baseline_person_add_disabled_24);
    }
}
