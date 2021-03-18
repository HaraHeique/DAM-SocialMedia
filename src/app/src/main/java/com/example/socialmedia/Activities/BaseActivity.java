package com.example.socialmedia.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.socialmedia.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    private static final int RESULT_REQUEST_PERMISSION = 200;

    protected ActionBar actionBar;
    protected Toolbar toolbar;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        context = getApplicationContext();
    }

    protected void setToolbarConfig(int toolbarId, String title, boolean backButton) {
        toolbar = findViewById(toolbarId);
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.white));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(backButton);
    }

    protected void setTitleActionBar(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void hideActionBar() {
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    protected void hideItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    protected void showItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setVisible(true);
        }
    }

    protected void disableItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setEnabled(false);
        }
    }

    protected void enableItemMenu(MenuItem menuItem) {
        if (menuItem != null) {
            menuItem.setEnabled(true);
        }
    }

    ////////// Permissão para acessar os recursos desejados /////////////
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
                    new AlertDialog.Builder(context)
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
}
