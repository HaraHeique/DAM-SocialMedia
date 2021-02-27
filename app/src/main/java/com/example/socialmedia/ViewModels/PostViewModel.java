package com.example.socialmedia.ViewModels;

import android.content.Context;
import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.example.socialmedia.Enums.PostType;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.Post;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.Utils.LoginSessionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostViewModel extends ViewModel {
    private final List<Post> postsList;

    public PostViewModel(Context context) {
        this.postsList = mockData(context);
    }

    public List<Post> getPostsList() {
        return this.postsList;
    }

    // Queria utilizar stream(), mas não é possível para API < 24
    public List<Post> getPostsByLogin(String login) {
        List<Post> result = new ArrayList<>();

        for (Post post: postsList) {
            if (post.user.login.equals(login)) {
                result.add(post);
            }
        }

        return result;
    }

    // Queria utilizar stream(), mas não é possível para API < 24
    public List<Post> getPostsByFollow(boolean following) {
        List<Post> result = new ArrayList<>();

        for (Post post: postsList) {
            if (post.user.following == following) {
                result.add(post);
            }
        }

        return result;
    }

    // Queria utilizar stream(), mas não é possível para API < 24
    public List<Post> getPostsNotLogin(String login) {
        List<Post> result = new ArrayList<>();

        for (Post post: postsList) {
            if (!post.user.login.equals(login)) {
                result.add(post);
            }
        }

        return result;
    }

    private List<Post> mockData(Context context) {
        Resources resources = context.getResources();
        CurrentUser currentUser = LoginSessionUtil.getCurrentInfo(context);

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

        Post post1Joaquin = new Post(
            "Minha foto de prêmio",
            "Foto que recebi no prêmio do filme do Coringa.",
            ImageUtil.getDrawable(resources, R.drawable.post_1_joaquin),
            PostType.IMAGE,
            usuarioJoaquin
        );

        Post post1Joaquina = new Post(
            "VIAGEM INCRÍVEL!",
            "Que viagem legal e com ótimas praias.",
            ImageUtil.getDrawable(resources, R.drawable.post_1_joaquina),
            PostType.IMAGE,
            usuarioJoaquina
        );

        Post post2Joaquina = new Post(
            "Espero voltar lá de novo!",
            "Com certeza irei voltar para aquelas prais lindas novamente do Nordeste!",
            null,
            PostType.COMMENT,
            usuarioJoaquina
        );

        Post post1avatar = new Post(
            "Eu sou o AVATAR!",
            "Irei proteger o equilibrío de todo mundo. Conte comigo!",
            ImageUtil.getDrawable(resources, R.drawable.post_1_avatar),
            PostType.IMAGE,
            usuarioAvatar
        );

        if (currentUser.isLogged) {
            User usuarioLogado = new User(
                currentUser.name,
                currentUser.login,
                DateTimeUtil.ConvertToDate(currentUser.bornDate),
                currentUser.city,
                true,
                ImageUtil.getDrawable(currentUser.pathImgProfile)
            );

            Post post1usuariologado = new Post(
                "Eu não sou o AVATAR!",
                "Uns pensam que sou avatar porque sou careca. Mas não sou.",
                null,
                PostType.COMMENT,
                usuarioLogado
            );

            Post post2usuariologado = new Post(
                "Galaxy!",
                "Best place to live far away from humans problems!",
                ImageUtil.getDrawable(resources, R.drawable.post2_usuariologado),
                PostType.IMAGE,
                usuarioLogado
            );

            return Arrays.asList(
                post1avatar, post1usuariologado, post1Joaquin, post1Joaquina, post2Joaquina,
                post2usuariologado
            );
        }

        return Arrays.asList(
            post1avatar, post1Joaquin, post1Joaquina, post2Joaquina
        );
    }
}
