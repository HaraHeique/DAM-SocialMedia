package com.example.socialmedia.Adapters.ViewHolders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

public abstract class PostViewHolder extends RecyclerView.ViewHolder {

    private final Resources resources;
    private final Context context;

    private final TextView descriptionTv;
    private final TextView dateCreatedTv;
    private final TextView userNameTv;
    private final TextView userLoginTv;
    private final ImageView userAvatarTv;
    private final ImageView followingIv;
    private final ImageView commentIv;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        this.resources = itemView.getResources();
        this.context = itemView.getContext();

        this.descriptionTv = itemView.findViewById(R.id.tv_timeline_description);
        this.dateCreatedTv = itemView.findViewById(R.id.tv_timeline_data);
        this.userNameTv = itemView.findViewById(R.id.tv_timeline_name);
        this.userLoginTv = itemView.findViewById(R.id.tv_timeline_login);
        this.userAvatarTv = itemView.findViewById(R.id.imv_timeline_avatar);
        this.followingIv = itemView.findViewById(R.id.imv_timeline_follow);
        this.commentIv = itemView.findViewById(R.id.imv_timeline_comment);
    }

    public void bind(Post post) {
        String formatDate = DateTimeUtil.ConvertToStrDateTime(post.createDate);
        CurrentUser currentUser = AppConfig.getCurrentUser(this.context);

        this.descriptionTv.setText(post.description);
        this.dateCreatedTv.setText(formatDate);
        this.userNameTv.setText(post.user.name);
        this.userLoginTv.setText(post.user.login);
        this.userAvatarTv.setImageBitmap(ImageUtil.base64ToBitmap(post.user.avatar));

        if (currentUser.isLogged && !currentUser.login.equalsIgnoreCase(post.user.login)) {
            Drawable followingIcon = this.getFollowingIcon(post.user.following);
            this.followingIv.setImageDrawable(followingIcon);
            followingIv.setVisibility(View.VISIBLE);
        } else {
            this.followingIv.setVisibility(View.GONE);
        }

        if (!currentUser.isLogged) {
            this.commentIv.setVisibility(View.GONE);
        }
    }

    private Drawable getFollowingIcon(boolean following) {
        if (!following) {
            return ImageUtil.getDrawable(this.resources, R.drawable.ic_baseline_person_add_24);
        }

        return ImageUtil.getDrawable(this.resources, R.drawable.ic_baseline_person_add_disabled_24);
    }
}
