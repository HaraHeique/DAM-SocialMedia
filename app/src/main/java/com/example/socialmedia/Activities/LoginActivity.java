package com.example.socialmedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.Utils.LayoutUtil;
import com.example.socialmedia.Utils.LoginSessionUtil;

public class LoginActivity extends AppCompatActivity {

    private static final int NEW_USER_RESULT = 1;
    private final Context CONTEXT = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LayoutUtil.hideActionBar(getSupportActionBar());
        onBtnClickRegisterNewUser();
        onBtnClickLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoToMainActivity();
    }

    private void onBtnClickRegisterNewUser() {
        Button btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CONTEXT, RegisterUserActivity.class);
                startActivityForResult(intent, NEW_USER_RESULT);
            }
        });
    }

    private void onBtnClickLogin() {
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = ((EditText)findViewById(R.id.et_login)).getText().toString();
                String password = ((EditText)findViewById(R.id.et_password)).getText().toString();

                if (!ValidateUserLogin(login, password)) {
                    return;
                }

                LoginSessionUtil.setLogin(CONTEXT, true);
                GoToMainActivity();
            }
        });
    }

    private void SetUserInfoLogin(Intent data) {
        if (data == null) {
            return;
        }

        String pathImgProfile = data.getStringExtra("pathImgProfile");
        String name = data.getStringExtra("name");
        String login = data.getStringExtra("login");

        // Isto não é seguro, mas por enquanto não tem comunicação com o server deixar assim
        String password = data.getStringExtra("password");

        CurrentUser user = new CurrentUser(login, name, password, pathImgProfile);

        LoginSessionUtil.setCurrentUserInfo(CONTEXT, user);
    }

    private boolean ValidateUserLogin(String login, String password) {
        CurrentUser user = LoginSessionUtil.getCurrentInfo(CONTEXT);

        if (user.login.equals(login) && user.password.equals(password)) {
            return true;
        }

        AlertMessageUtil.defaultAlert(CONTEXT, "Login ou Senha incorretos!");

        return false;
    }

    private void GoToMainActivity() {
        if (LoginSessionUtil.isLogged(CONTEXT)) {
            Intent intent = new Intent(CONTEXT, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_RESULT && resultCode == Activity.RESULT_OK) {
            SetUserInfoLogin(data);
        }
    }
}