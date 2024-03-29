package com.example.socialmedia.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ImageUtil {

    public static int calculateNumberOfColumns(Context context, float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels;

        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }

    public static Bitmap getBitmap(String imagePath, int w, int h) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.max(photoW/w, photoH/h);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }

    public static Bitmap getBitmap(Resources resources, int drawableId, int w, int h) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableId);
        bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);

        return bitmap;
    }

    public static Bitmap getBitmap(String imagePath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, bmOptions);
    }

    public static Bitmap getBitmap(Context context, Uri imageLocation, int newWidth, int newHeight) throws FileNotFoundException {
        InputStream is = context.getContentResolver().openInputStream(imageLocation);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(photoW/newWidth, photoH/newHeight);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        is = context.getContentResolver().openInputStream(imageLocation);
        return BitmapFactory.decodeStream(is, null, bmOptions);
    }

    public static Bitmap getBitmap(String imagePath, int w, int h, boolean autoRotate) {
        Bitmap image = getBitmap(imagePath, w, h);

        return autoRotate ? autoRotateImage(image, imagePath) : image;
    }

    public static Bitmap getBitmap(String imagePath, boolean autoRotate) {
        Bitmap image = getBitmap(imagePath);

        return autoRotate ? autoRotateImage(image, imagePath) : image;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap autoRotateImage(Bitmap bitmap, String photoPath) {
        Bitmap rotatedBitmap = null;

        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotatedBitmap;
    }

    public static Bitmap base64ToBitmap(String imgData) {
        imgData = imgData.substring(imgData.indexOf(",") + 1);
        byte[] imageAsBytes = Base64.decode(imgData.getBytes(), Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static Drawable getDrawable(Resources resources, int drawableId) {
        return resources.getDrawable(drawableId);
    }

    public static Drawable getDrawable(String imagePath) {
        return Drawable.createFromPath(imagePath);
    }

    public static File getImageFile(String imagePath, Bitmap bitmap) {
        File imageFile = new File(imagePath);

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    public static File getImageFile(String imagePath, int w, int h) {
        Bitmap bitmap = getBitmap(imagePath, w, h);

        return getImageFile(imagePath, bitmap);
    }
}
