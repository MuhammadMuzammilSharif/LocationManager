/*
 * Copyright (C) 2017 Muhammad Muzammil Sharif
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.muhammadmuzammilsharif.locationhelper.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by M_Muzammil Sharif on 10-Apr-17.
 */

public class PermissionHandlerHelper {
    public static void checkPermissionHelper(@NonNull Context context, @NonNull String[] permissions, @NonNull CheckPermissionResponse response) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            response.permissionGranted();
            return;
        }
        if (checkIsPermissionsGranted((Activity) context, permissions, 0)) {
            response.permissionGranted();
        } else {
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                    response.showNeededPermissionDialog();
                    return;
                }
            }
            response.requestPermission();
        }
    }

    public interface CheckPermissionResponse {
        public void permissionGranted();

        public void showNeededPermissionDialog();

        public void requestPermission();
    }

    public interface PermissionResultResponse {
        public void permissionDenied();

        public void permissionGranted();
    }

    private static boolean checkIsPermissionsGranted(Activity activity, String[] permissions, int pos) {
        int permission = ContextCompat.checkSelfPermission(activity,
                permissions[pos]);
        return permission == PackageManager.PERMISSION_GRANTED && (permissions.length <= (pos + 1) || (checkIsPermissionsGranted(activity, permissions, pos + 1)));
    }

    public static void onRequestPermissionsResultHelper(@NonNull Context context, @NonNull String[] permissions, @NonNull PermissionResultResponse response) {
        if (checkIsPermissionsGranted(((Activity) context), permissions, 0)) {
            response.permissionGranted();
        } else {
            response.permissionDenied();
        }
    }
}
