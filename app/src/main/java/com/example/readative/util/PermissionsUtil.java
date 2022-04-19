package com.example.readative.util;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class PermissionsUtil {

    /**
     * @param context    The context of an Activity or a Fragment that requires the permission.
     * @param permission Permission name. Reference {@link Manifest.permission} for a list of permission name constants.
     * @return {@code false} if permission is denied,
     * {@code true} when permission is granted.
     */
    public static boolean checkPermission(
            @NonNull Context context,
            @NonNull String permission) {
        switch (permission) {
            case READ_EXTERNAL_STORAGE:
            case WRITE_EXTERNAL_STORAGE:
            case MANAGE_EXTERNAL_STORAGE:
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    return Environment.isExternalStorageManager();
                }
                break;
        }
        int result = ContextCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Runs {@link #checkPermission(Context, String)} on the list of permissions.
     *
     * @param context     The context of an Activity or a Fragment that requires the permission.
     * @param permissions A list of permissions from {@link Manifest.permission}.
     * @return A map of permission names to result of {@link #checkPermission(Context, String)}.
     */
    public static Map<String, Boolean> checkPermissions(
            @NonNull Context context,
            @NonNull String... permissions) {
        Map<String, Boolean> resultMap = new HashMap<>();
        for (String permission : permissions) {
            boolean result = checkPermission(context, permission);
            resultMap.put(permission, result);
        }
        return resultMap;
    }


    /**
     * @param caller     Activity or Fragment on behalf of which the requests are made.
     * @param callback   A callback for request result.
     * @param permission Name of the permission to request. Reference {@link Manifest.permission}.
     */
    public void requestPermission(
            @NonNull ActivityResultCaller caller,
            @NonNull OnPermissionResult callback,
            @NonNull String permission) {
        ActivityResultLauncher<Intent> activityResultLauncher = caller.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK)
                        callback.onPermissionGranted(permission);
                    else callback.onPermissionDenied(permission);
                });
        ActivityResultLauncher<String> requestPermissionLauncher = caller.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) callback.onPermissionGranted(permission);
                    else callback.onPermissionDenied(permission);
                });

        switch (permission) {
            case READ_EXTERNAL_STORAGE:
            case WRITE_EXTERNAL_STORAGE:
            case MANAGE_EXTERNAL_STORAGE:
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        intent.addCategory("android.intent.category.DEFAULT");
//                        intent.setData(Uri.parse(String.format("package:%s", caller.getApplicationContext().getPackageName())));
                        activityResultLauncher.launch(intent);
                    } catch (Exception e) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        activityResultLauncher.launch(intent);
                    }
                }
                break;
        }
        requestPermissionLauncher.launch(permission);
    }

    /**
     * @param caller      Activity or Fragment on behalf of which the requests are made.
     * @param callback    A callback for request result.
     * @param permissions Names of the permissions to request. Reference {@link Manifest.permission}.
     */
    public void requestPermissions(
            @NonNull ActivityResultCaller caller,
            @NonNull OnPermissionResult callback,
            @NonNull String... permissions) {
        ActivityResultLauncher<String[]> requestPermissionLauncher = caller.registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), grantedMap -> {
                    for (Map.Entry<String, Boolean> permission : grantedMap.entrySet()) {
                        if (permission.getValue())
                            callback.onPermissionGranted(permission.getKey());
                        else callback.onPermissionDenied(permission.getKey());
                    }
                });
        if (SDK_INT >= Build.VERSION_CODES.R) {
            AtomicBoolean hasStoragePermission = new AtomicBoolean(false);
            permissions = (String[]) Arrays.stream(permissions).filter(value -> {
                if (value.equals(READ_EXTERNAL_STORAGE) ||
                        value.equals(WRITE_EXTERNAL_STORAGE) ||
                        value.equals(MANAGE_EXTERNAL_STORAGE)) {
                    hasStoragePermission.set(true);
                    return false;
                } else {
                    return true;
                }
            }).toArray();

            if (hasStoragePermission.get()) {
                requestPermission(caller, callback, MANAGE_EXTERNAL_STORAGE);
            }
        }
        requestPermissionLauncher.launch(permissions);
    }


    /**
     * Callback interface.
     */
    public interface OnPermissionResult {
        /**
         * Called when permission request is accepted.
         */
        void onPermissionGranted(String permission);

        /**
         * Called when permission request is denied.
         */
        void onPermissionDenied(String permission);
    }
}
