package com.example.socialmedia.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.socialmedia.R;
import com.example.socialmedia.Utils.DateTimeUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public abstract class BaseActivity extends AppCompatActivity {

    protected ActionBar actionBar;
    protected Toolbar toolbar;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected void setToolbarConfig(int toolbarId, String title, boolean backButton) {
        toolbar = findViewById(toolbarId);
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.white));
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(backButton);
        }
    }

    protected void setTitleActionBar(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void setColorItemView(View itemView, int colorValue) {
        if (itemView == null) { return; }

        if (itemView instanceof TextView) {
            ((TextView) itemView).setTextColor(colorValue);
        } else if (itemView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) itemView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setColorItemView(viewGroup.getChildAt(i), colorValue);
            }
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

    protected String takePictureIntent(final int takePictureResult) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = null;

            try {
                file = createImageFile();
            } catch (IOException e) {
                Toast.makeText(context, "Não foi possível criar o arquivo.", Toast.LENGTH_LONG).show();
                return "";
            }

            String authority = context.getPackageName() + ".provider";
            Uri fUri = FileProvider.getUriForFile(context, authority, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);
            startActivityForResult(intent, takePictureResult);

            return file.getAbsolutePath();
        }

        return "";
    }

    private File createImageFile() throws IOException {
        String dateTimeNow = DateTimeUtil.convertToStrDateTime(new Date(), "dd_MM_yyyy_HH_mm_ss");
        String filename = "JPEG_" + dateTimeNow + "_";
        filename = filename.replace('/', '_');
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(filename, ".jpg", storageDir);
    }
}
