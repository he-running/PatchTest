package com.he.patchtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

/**
 * Created by he on 2017/12/11.
 */

public class ApkUtil {

    public static String getApkVersionName(Context context){
        try {
            PackageManager manager=context.getApplicationContext().getPackageManager();
            PackageInfo info=manager.getPackageInfo(context.getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       return "";
    }

    public static String getApkPath(Context context){
        ApplicationInfo appInfo=context.getApplicationContext().getApplicationInfo();
        String apkPath=appInfo.sourceDir;
        return apkPath;
    }

    public static void installApk(Context context, String apkPath){
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(new File(apkPath)),"application/vnd.android.package-archive");
        context.startActivity(i);
    }
}
