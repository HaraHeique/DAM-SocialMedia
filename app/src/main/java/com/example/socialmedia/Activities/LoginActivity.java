package com.example.socialmedia.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.Utils.LoginSessionUtil;

public class LoginActivity extends BaseActivity {

    private static final int NEW_USER_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hideActionBar();
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

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegisterUserActivity.class);
            startActivityForResult(intent, NEW_USER_RESULT);
        });
    }

    private void onBtnClickLogin() {
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String login = ((EditText)findViewById(R.id.et_login)).getText().toString();
            String password = ((EditText)findViewById(R.id.et_password)).getText().toString();

            if (!ValidateUserLogin(login, password)) {
                return;
            }

            LoginSessionUtil.setLogin(context, true);
            GoToMainActivity();
        });
    }

    private void SetUserInfoLogin(Intent data) {
        if (data == null) {
            return;
        }

        String pathImgProfile = data.getStringExtra("pathImgProfile");
        String name = data.getStringExtra("name");
        String login = data.getStringExtra("login");
        String bornDate = data.getStringExtra("bornDate");
        String city = data.getStringExtra("city");

        // Isto não é seguro, mas por enquanto não tem comunicação com o server deixar assim
        String password = data.getStringExtra("password");

        CurrentUser user = new CurrentUser(login, name, bornDate, city, password, pathImgProfile);

        LoginSessionUtil.setCurrentUserInfo(context, user);
    }

    private boolean ValidateUserLogin(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            AlertMessageUtil.defaultAlert(context, "Campos de Login e Senha são requeridos!");
            return false;
        }

        CurrentUser user = LoginSessionUtil.getCurrentInfo(context);

        if (user.login.equals(login) && user.password.equals(password)) {
            return true;
        }

        AlertMessageUtil.defaultAlert(context, "Login ou Senha incorretos!");

        return false;
    }

    private void GoToMainActivity() {
        if (LoginSessionUtil.isLogged(context)) {
            Intent intent = new Intent(context, MainActivity.class);
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