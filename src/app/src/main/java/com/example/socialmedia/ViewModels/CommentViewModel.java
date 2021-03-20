package com.example.socialmedia.ViewModels;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.Comment;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

import java.util.Arrays;
import java.util.List;

public class CommentViewModel extends ViewModel {

    private final List<Comment> commentsList;

    public CommentViewModel(Context context) {
        this.commentsList = mockData(context);
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    private List<Comment> mockData(Context context) {
        Resources resources = context.getResources();
        CurrentUser currentUser = AppConfig.getCurrentUser(context);

        User usuarioJoaquin = new User(
            "Joaquin Phoenix",
            "joaquin123",
            DateTimeUtil.ConvertToDate("05/12/1970"),
            "Cidade 1",
            false,
            ImageUtil.getDrawable(resources, R.drawable.avatar_joaquin)
        );

        User usuarioJoaquina = new User(
            "Joaquina de Aleluia",
            "joaquina@t42",
            DateTimeUtil.ConvertToDate("23/02/1992"),
            "Cidade 2",
            false,
            ImageUtil.getDrawable(resources, R.drawable.avatar_joaquina)
        );

        User usuarioAvatar = new User(
            "Avatar Aang",
            "aang@original",
            DateTimeUtil.ConvertToDate("17/06/1612"),
            "Cidade dos Monges",
            true,
            ImageUtil.getDrawable(resources, R.drawable.avatar_avatar)
        );

        User usuarioLogado = new User(
            currentUser.name,
            currentUser.login,
            DateTimeUtil.ConvertToDate(currentUser.bornDate),
            currentUser.city,
            true,
            ImageUtil.getDrawable(currentUser.pathImgProfile)
        );

        Comment comment1Joaquin = new Comment(
            "Testando o comentario.",
            usuarioJoaquin
        );

        Comment comment1Joaquina = new Comment(
            "QUE COMENTÁRIO INCRÍVEL! #SQN",
            usuarioJoaquina
        );

        Comment comment2Joaquina = new Comment(
            "Espero não ter que ler isso novamente...",
            usuarioJoaquina
        );

        Comment comment1avatar = new Comment(
            "Eu sou o AVATAR!",
            usuarioAvatar
        );

        Comment comment1usuarioLogado = new Comment(
            "KKK xD!",
            usuarioLogado
        );

        return Arrays.asList(
            comment1avatar, comment1Joaquin, comment1Joaquina, comment2Joaquina, comment1usuarioLogado
        );
    }
}
