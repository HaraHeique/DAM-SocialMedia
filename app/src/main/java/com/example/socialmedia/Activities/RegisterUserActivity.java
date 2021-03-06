package com.example.socialmedia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.socialmedia.R;
import com.example.socialmedia.Utils.AlertMessageUtil;
import com.example.socialmedia.Utils.DateTimeUtil;
import com.example.socialmedia.Utils.ImageUtil;

import java.io.File;
import java.io.IOException;

public class RegisterUserActivity extends BaseActivity {

    private static final int RESULT_TAKE_PICTURE = 1;


    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        setToolbarConfig(R.id.tb_register_user, "Novo Usuário", true);
        onClickPhotoPicker();
        onClickBtnConfirmRegisterUser();
    }

    private void onClickBtnConfirmRegisterUser() {
        Button btnConfirmRegister = findViewById(R.id.btn_newuser_confirm);

        btnConfirmRegister.setOnClickListener(v -> {
            String login = ((EditText)findViewById(R.id.et_newuser_login)).getText().toString();
            String password = ((EditText)findViewById(R.id.et_newuser_password)).getText().toString();
            String confirmPassword = ((EditText)findViewById(R.id.et_newuser_confirmpassword)).getText().toString();
            String name = ((EditText)findViewById(R.id.et_newuser_name)).getText().toString();
            String bornDate = ((EditText)findViewById(R.id.et_newuser_borndate)).getText().toString();
            String city = ((EditText)findViewById(R.id.et_newuser_city)).getText().toString();
            String imageProfile = currentPhotoPath;

            if (!ValidateLogin(login) || !ValidatePassword(password) ||
                !ValidateConfirmPassword(password, confirmPassword) || !ValidateName(name) ||
                !ValidateBornDate(bornDate) || !ValidateCity(city) ||
                !ValidateImage(imageProfile)) {
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("login", login);
            intent.putExtra ("bornDate", bornDate);
            intent.putExtra("password", password);
            intent.putExtra("city", city);
            intent.putExtra("pathImgProfile", imageProfile);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    private void onClickPhotoPicker() {
        ImageView btnImg = findViewById(R.id.imv_newuser_imgprofile);

        btnImg.setOnClickListener(v -> dispatchTakePictureIntent());
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = null;

        try {
            file = createImageFile();
        } catch (IOException e) {
            Toast.makeText(context, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        currentPhotoPath = file.getAbsolutePath();
        String authority = this.getApplicationContext().getPackageName() + ".provider";
        Uri fUri = FileProvider.getUriForFile(context, authority, file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
        startActivityForResult(intent, RESULT_TAKE_PICTURE);
    }

    private File createImageFile() throws IOException {
        String filename = "perfil";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(filename, ".jpg", storageDir);
    }

    // Após ser executado o intent da câmera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap imgBitmap = ImageUtil.getBitmap(currentPhotoPath);
                imgBitmap = ImageUtil.autoRotateImage(imgBitmap, currentPhotoPath);
                ImageView imageView = findViewById(R.id.imv_newuser_imgprofile);
                imageView.setImageBitmap(imgBitmap);
            } else {
                new File(currentPhotoPath).delete();
            }
        }
    }

    // Validações do form de cadastro //
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

    private boolean ValidateBornDate(String date) {
        if (DateTimeUtil.ConvertToDate(date) == null) {
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