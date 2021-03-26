package com.example.socialmedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Activities.PostActivity;
import com.example.socialmedia.Adapters.ViewHolders.PostCommentViewHolder;
import com.example.socialmedia.Adapters.ViewHolders.PostImageViewHolder;
import com.example.socialmedia.Adapters.ViewHolders.PostViewHolder;
import com.example.socialmedia.Enums.PostType;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.R;

import java.util.Collections;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private List<Post> posts;

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
        Post post = this.posts.get(position);
        holder.bind(post);
        this.onClickBtnIconFollow(holder.itemView, post.user.login);
        this.onClickBtnIconComment(holder.itemView, post.id);
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.posts.get(position).type.ordinal();
    }

    public void updatePostList(List<Post> posts) {
        this.posts = posts;
        notifyPostsListChanged();
    }

    public void notifyPostsListChanged() {
        Collections.sort(this.posts, Collections.reverseOrder());
        notifyDataSetChanged();
    }

    private void onClickBtnIconFollow(View itemView, String who) {
        ImageView btnFollow = itemView.findViewById(R.id.imv_timeline_follow);
        Object btnFollowTag = btnFollow.getTag();

        if (btnFollowTag != null) {
            boolean following = (Boolean)btnFollowTag;
            btnFollow.setOnClickListener(v -> ((PostActivity)v.getContext()).changeStateFollow(who, following));
        }
    }

    private void onClickBtnIconComment(View itemView, String id) {
        ImageView btnComment = itemView.findViewById(R.id.imv_timeline_comment);
        btnComment.setOnClickListener(v -> ((PostActivity)v.getContext()).startCommentActivity(id));
    }
}
