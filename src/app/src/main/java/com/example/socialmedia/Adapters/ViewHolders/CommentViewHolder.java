package com.example.socialmedia.Adapters.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Models.Comment;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private final ImageView userAvatarIv;
    private final TextView userNameIv;
    private final TextView descriptionTv;
    private final TextView dateTv;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        this.userAvatarIv = itemView.findViewById(R.id.imv_comment_avatar);
        this.userNameIv = itemView.findViewById(R.id.tv_comment_name);
        this.descriptionTv = itemView.findViewById(R.id.tv_comment_description);
        this.dateTv = itemView.findViewById(R.id.tv_comment_date);
    }

    public void bind(Comment comment) {
        this.userAvatarIv.setImageBitmap(ImageUtil.base64ToBitmap(comment.user.avatar));
        this.userNameIv.setText(comment.user.name);
        this.descriptionTv.setText(comment.description);
        this.dateTv.setText(DateTimeUtil.convertToStrDateTime(comment.createDate));
    }
}
