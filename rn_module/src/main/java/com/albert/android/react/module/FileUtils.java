package com.albert.android.react.module;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * <pre>
 *      Copyright    : Copyright (c) 2019.
 *      Author       : jiaoya.
 *      Created Time : 2019-10-09.
 *      Desc         :
 * </pre>
 */
public class FileUtils {


    public static void dsdsd(Context context) throws IOException {
        File file = getAsseets(context, "RnDevelop.zip");
        unzipBundleFile(file);
    }

    /**
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File getAsseets(Context context, String fileName) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(fileName);
        File file = new File(context.getExternalFilesDir(null), "RnDevelop.zip");
        inputStream2File(inputStream, file);
        return file;
    }


    /**
     * 将inputStream转化为file
     *
     * @param is
     * @param file 要输出的文件目录
     */
    public static void inputStream2File(InputStream is, File file) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[8192];

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } finally {
            os.close();
            is.close();
        }
    }

    public static String getBundleJsFile(Context context) {

        return context.getExternalFilesDir(null) + "/package/main.jsbundle";
    }

    private static boolean unzipBundleFile(File bundleZipFile) {
        Random random = new Random(System.currentTimeMillis());
        File targetDir = new File(bundleZipFile.getParentFile(), random.nextLong() + "");
        if (unzip(bundleZipFile, targetDir)) {
            File packageFile = new File(bundleZipFile.getParentFile(), "package");
//            RnFileUtil.deleteFile(packageFile);
            boolean success = targetDir.renameTo(packageFile);
            return success;
        } else {//解压失败则删除压缩文件和解压出来的数据不完整的文件目录
            return false;
        }
    }

    public static boolean unzip(File zipSrcFile, File targetDir) {
        try {
            if (zipSrcFile.exists()) {
                ZipFile zipFile = new ZipFile(zipSrcFile);
                Enumeration e = zipFile.entries();
                while (e.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) e.nextElement();
                    if (entry.isDirectory()) continue;
                    InputStream is = zipFile.getInputStream(entry);
                    File dstFile = new File(targetDir + "/" + entry.getName());
                    File parentFile = dstFile.getParentFile();
                    if (!parentFile.exists()) parentFile.mkdirs();
                    FileOutputStream fos = new FileOutputStream(dstFile);
                    byte[] buffer = new byte[8192];
                    int count = 0;
                    while ((count = is.read(buffer, 0, buffer.length)) != -1) {
                        fos.write(buffer, 0, count);
                    }
                }
                return true;
            }
        } catch (Throwable t) {
            deleteFile(targetDir);
            t.printStackTrace();
        }
        return false;
    }


    public static boolean deleteFile(File file) {
        try {
            if (!file.exists()) {
                return true;
            } else if (file.isFile()) {
                return file.delete();
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (!deleteFile(f)) {
                        return false;
                    }
                }
                return file.delete();
            }
        } catch (Exception e) {
            return false;
        }
    }

}
