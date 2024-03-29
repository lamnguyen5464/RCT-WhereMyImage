package com.wheremyimages.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.wheremyimages.nativemodules.permission.utils.PermissionHelper;

import java.util.ArrayList;

public class GalleryHelper {
    public static ArrayList<String> fetchGalleryImages(final Activity activity) {
        if (!PermissionHelper.isGranted(activity, Manifest.permission.READ_EXTERNAL_STORAGE)){
            return new ArrayList<>();
        }

        ArrayList<String> galleryImageUrls;
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};//get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date

        Cursor imagecursor = activity.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        galleryImageUrls = new ArrayList<String>();

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            String imageUrl = "file://" + imagecursor.getString(dataColumnIndex);
            galleryImageUrls.add(imageUrl);//get Image from column index
        }
        return galleryImageUrls;
    }
}
