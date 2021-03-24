package com.example.socialmedia.Adapters.ViewHolders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FriendViewHolder extends RecyclerView.ViewHolder {

    private final Resources resources;

    private final ImageView userAvatarIv;
    private final ImageView userFollowIv;
    private final TextView userLoginTv;
    private final TextView userNameAgeTv;
    private final TextView userCityTv;

    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);

        this.resources = itemView.getResources();

        this.userAvatarIv = itemView.findViewById(R.id.imv_user_avatar);
        this.userFollowIv = itemView.findViewById(R.id.imv_user_follow);
        this.userLoginTv = itemView.findViewById(R.id.tv_user_login);
        this.userNameAgeTv = itemView.findViewById(R.id.tv_user_name_age);
        this.userCityTv = itemView.findViewById(R.id.tv_user_city);
    }

    public void bind(User user) {
        this.userAvatarIv.setImageBitmap(ImageUtil.base64ToBitmap(user.avatar));
        this.userFollowIv.setImageDrawable(this.getFollowingIcon(user.following));
        this.userLoginTv.setText(user.login);
        this.userNameAgeTv.setText(this.getNameAgeFormattedText(user));
        this.userCityTv.setText(user.city);
    }

    private Drawable getFollowingIcon(boolean following) {
        if (!following) {
            return ImageUtil.getDrawable(this.resources, R.drawable.ic_baseline_person_add_24);
        }

        return ImageUtil.getDrawable(this.resources, R.drawable.ic_baseline_person_add_disabled_24);
    }

    private String getNameAgeFormattedText(User user) {
        int age = DateTimeUtil.getDiffYears(user.bornDate, new Date());

        return String.format("%s, %d anos.", user.name, age);
    }
}
