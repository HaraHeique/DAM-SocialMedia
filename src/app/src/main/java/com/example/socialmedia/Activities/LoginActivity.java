package com.example.socialmedia.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.AppConfig;
import com.example.socialmedia.Models.CurrentUser;
import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.ViewModels.LoginViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private static final int NEW_USER_RESULT = 1;
    private static final int RESULT_REQUEST_PERMISSION = 200;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        hideActionBar();
        onBtnClickRegisterNewUser();
        onBtnClickLogin();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeLogin();
        askForPermissions(Collections.singletonList(Manifest.permission.CAMERA));
    }

    @Override
    protected void onResume() {
        super.onResume();

        startPostActivity();
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
            String appToken = AppConfig.getAppToken(context);

            if (!validateUserLogin(login, password)) { return; }

            loginViewModel.login(login, password, appToken);
        });
    }

    private boolean validateUserLogin(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            AlertMessageUtil.defaultAlert(context, "Campos de Login e Senha são requeridos!");
            return false;
        }

        return true;
    }

    private void observeLogin() {
        loginViewModel.observeLogin().observe(this, objResponse -> {
            AlertMessageUtil.defaultAlert(context, objResponse.message);

            if (objResponse.success) {
                setUserInfoLogin(objResponse.data);
                startPostActivity();
            }
        });
    }

    private void setUserInfoLogin(User user) {
        CurrentUser currentUser = new CurrentUser(user.login, user.authToken);

        AppConfig.setCurrentUser(context, currentUser);
        AppConfig.setLogin(context,true);
    }

    private void startPostActivity() {
        if (AppConfig.isLogged(context)) {
            Intent intent = new Intent(context, PostActivity.class);
            startActivity(intent);
        }
    }

    private void setInfoAfterRegisterUser(Intent data) {
        if (data == null) { return; }

        EditText etLogin = findViewById(R.id.et_login);
        EditText etPassword = findViewById(R.id.et_password);

        etLogin.setText(data.getStringExtra("login"));
        etPassword.setText(data.getStringExtra("password"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_RESULT && resultCode == Activity.RESULT_OK) {
            setInfoAfterRegisterUser(data);
        }
    }

    //region Permissão para acessar os recursos desejados
    protected void askForPermissions(List<String> permissions) {
        List<String> permissionNotGranted = new ArrayList<>();

        for (String permission : permissions) {
            if (hasPermission(permission)) {
                permissionNotGranted.add(permission);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionNotGranted.size() > 0) {
                requestPermissions(permissionNotGranted.toArray(new String[permissionNotGranted.size()]), RESULT_REQUEST_PERMISSION);
            }
        }
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        List<String> permissionsRejected = new ArrayList<>();

        if (requestCode == RESULT_REQUEST_PERMISSION) {
            for (String permission : permissions) {
                if (hasPermission(permission)) {
                    permissionsRejected.add(permission);
                }
            }
        }

        // Pergunta por permissão novamente
        if (permissionsRejected.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("Para utilizar este APP é preciso conceder essas permissões!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                                }
                            })
                            .create()
                            .show();
                }
            }
        }
    }
    //endregion
}