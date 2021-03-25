package com.example.socialmedia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.Adapters.ViewHolders.CommentViewHolder;
import com.example.socialmedia.Models.Comment;
import com.example.socialmedia.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_list_comment, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = this.comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }

    public void updateCommentList(List<Comment> commentsList) {
        this.comments = commentsList;
        notifyDataSetChanged();
    }
}
