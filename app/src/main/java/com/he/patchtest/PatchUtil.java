package com.he.patchtest;

/**
 * Created by he on 2017/12/11.
 */

public class PatchUtil {

    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 生成old.apk和new.apk的差分包
     * @param oldPath
     * @param newPath  新版apk的路径
     * @param patchPath
     * @return
     */
    public static native int generateDiffApk(String oldPath,String newPath,String patchPath);

    /**
     * 合并old.apk和差分包，生成新的安装包
     * @param oldPath
     * @param newPath 生成的新apk路径
     * @param patchPath
     * @return
     */
    public static native int mergeDiffApk(String oldPath, String newPath, String patchPath);
}
