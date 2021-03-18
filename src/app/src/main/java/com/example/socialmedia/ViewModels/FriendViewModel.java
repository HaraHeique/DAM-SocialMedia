package com.example.socialmedia.ViewModels;

import android.content.res.Resources;

import androidx.lifecycle.ViewModel;

import com.example.socialmedia.Models.Post;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendViewModel extends ViewModel {

    private final List<User> usersList;

    public FriendViewModel(Resources resources) {
        this.usersList = mockData(resources);
    }

    public List<User> getFriendsList() {
        return this.usersList;
    }

    public List<User> getFollowingList(boolean following) {
        // Queria utilizar stream(), mas não é possível para API < 24
        List<User> result = new ArrayList<>();

        for (User user: usersList) {
            if (user.following == following) {
                result.add(user);
            }
        }

        return result;
    }

    private List<User> mockData(Resources resources) {
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

        return Arrays.asList(
            usuarioAvatar, usuarioJoaquin, usuarioJoaquina
        );
    }
}
