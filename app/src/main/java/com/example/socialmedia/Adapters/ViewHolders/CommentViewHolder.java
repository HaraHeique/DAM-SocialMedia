package com.example.socialmedia.Adapters.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Models.Comment;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private final ImageView userAvatarIv;
    private final TextView userNameIv;
    private final TextView userLoginTv;
    private final TextView descriptionTv;
    private final TextView dateTv;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        this.userAvatarIv = itemView.findViewById(R.id.imv_comment_avatar);
        this.userNameIv = itemView.findViewById(R.id.tv_comment_name);
        this.userLoginTv = itemView.findViewById(R.id.tv_comment_login);
        this.descriptionTv = itemView.findViewById(R.id.tv_comment_description);
        this.dateTv = itemView.findViewById(R.id.tv_comment_date);
    }

    public void bind(Comment comment) {
        this.userAvatarIv.setImageDrawable(comment.user.avatar);
        this.userNameIv.setText(comment.user.name);
        this.userLoginTv.setText(comment.user.login);
        this.descriptionTv.setText(comment.description);
        this.dateTv.setText(DateTimeUtil.ConvertToStrDateTime(comment.createDate));
    }
}
