package com.example.socialmedia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.socialmedia.Models.User;
import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;
import com.example.socialmedia.ViewModels.RegisterUserViewModel;

import java.io.File;
import java.util.Date;

public class RegisterUserActivity extends BaseActivity {

    private static final int TAKE_PICTURE_RESULT = 1;

    private RegisterUserViewModel registerUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        setToolbarConfig(R.id.tb_register_user, "Novo Usuário", true);
        onClickPhotoPicker();
        onClickBtnConfirmRegisterUser();
        registerUserViewModel = new ViewModelProvider(this).get(RegisterUserViewModel.class);
        observeRegisterUser();
    }

    private void onClickPhotoPicker() {
        ImageView btnImg = findViewById(R.id.imv_newuser_imgprofile);

        btnImg.setOnClickListener(v -> {
            registerUserViewModel.avatarImagePath = takePictureIntent(TAKE_PICTURE_RESULT);
        });
    }

    private void onClickBtnConfirmRegisterUser() {
        Button btnConfirmRegister = findViewById(R.id.btn_newuser_confirm);

        btnConfirmRegister.setOnClickListener(v -> {
            String confirmPassword = ((EditText)findViewById(R.id.et_newuser_confirmpassword)).getText().toString();
            User registeredUser = new User(
                ((EditText)findViewById(R.id.et_newuser_name)).getText().toString(),
                ((EditText)findViewById(R.id.et_newuser_login)).getText().toString(),
                ((EditText)findViewById(R.id.et_newuser_password)).getText().toString(),
                DateTimeUtil.ConvertToDate(((EditText)findViewById(R.id.et_newuser_borndate)).getText().toString()),
                ((EditText)findViewById(R.id.et_newuser_city)).getText().toString()
            );

            if (!ValidateRegisterUser(registeredUser, confirmPassword)) { return; }

            registerUserViewModel.registerUser(registeredUser);
        });
    }

    private void observeRegisterUser() {
        registerUserViewModel.observeUserRegistered().observe(this, objResponse -> {
            AlertMessageUtil.defaultAlert(context, objResponse.message);

            if (objResponse.success) {
                finishActivityWithResult(objResponse.data);
            }
        });
    }

    private void finishActivityWithResult(User user) {
        Intent intent = new Intent();
        intent.putExtra("login", user.login);
        intent.putExtra("password", user.password);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    // Após ser executado o intent da câmera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE_RESULT && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.imv_newuser_imgprofile);
            Bitmap imgBitmap = ImageUtil.getBitmap(registerUserViewModel.avatarImagePath, true);
            imageView.setImageBitmap(imgBitmap);
        } else {
            new File(registerUserViewModel.avatarImagePath).delete();
        }
    }

    // Validações do form de cadastro
    private boolean ValidateRegisterUser(User user, String confirmPassword) {
        if (!ValidateLogin(user.login) || !ValidatePassword(user.password) ||
            !ValidateConfirmPassword(user.password, confirmPassword) || !ValidateName(user.name) ||
            !ValidateBornDate(user.bornDate) || !ValidateCity(user.city) ||
            !ValidateImage(registerUserViewModel.avatarImagePath)) {
            return false;
        }

        return true;
    }

    private boolean ValidateLogin(String value) {
        if (value.length() < 4) {
            AlertMessageUtil.defaultAlert(context, "Login deve conter no mínimo 4 caracteres.");
            return false;
        }

        return true;
    }

    private boolean ValidatePassword(String value) {
        if (value.length() < 6) {
            AlertMessageUtil.defaultAlert(context, "Senha deve conter no mínimo 6 caracteres.");
            return false;
        }

        return true;
    }

    private boolean ValidateConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            AlertMessageUtil.defaultAlert(context, "Confirmação de senha deve ser igual a senha.");
            return false;
        }

        return true;
    }

    private boolean ValidateName(String name) {
        if (name.length() < 6) {
            AlertMessageUtil.defaultAlert(context, "Nome completo deve conter no mínimo 6 caracteres.");
            return false;
        }

        return true;
    }

    private boolean ValidateBornDate(Date date) {
        if (date == null) {
            AlertMessageUtil.defaultAlert(context, "Data deve ser no formato dd/MM/yyyy");
            return false;
        }

        return true;
    }

    private boolean ValidateCity(String value) {
        if (value.length() < 4) {
            AlertMessageUtil.defaultAlert(context, "Cidade deve conter no mínimo 4 caracteres.");
            return false;
        }

        return true;
    }

    private boolean ValidateImage(String pathImage) {
        String message = "Deve conter uma foto válida de perfil.";

        if (pathImage == null || pathImage.isEmpty()) {
            AlertMessageUtil.defaultAlert(context, message);
            return false;
        }

        File file = new File(pathImage);

        if (file == null || !file.exists()) {
            AlertMessageUtil.defaultAlert(context, message);
            return false;
        }

        return true;
    }
}