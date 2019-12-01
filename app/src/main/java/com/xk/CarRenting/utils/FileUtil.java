/*
 * AUTHOR：YOLANDA
 *
 * DESCRIPTION：create the File, and add the content.
 *
 * Copyright © ZhiMore. All Rights Reserved
 *
 */
package com.xk.CarRenting.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created in Sep 10, 2015 4:22:18 PM.
 *
 * @author YOLANDA;
 */
public class FileUtil {

    /**
     * SD.
     */
    public static File getRootPath() {
        File path = null;
        if (FileUtil.sdCardIsAvailable()) {
            path = Environment.getExternalStorageDirectory(); // sdcard
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }

    /**
     * SD.
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else
            return false;
    }

    /**
     * .
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * , .
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File exeFile = files[i];
            if (exeFile.isDirectory()) {
                delAllFile(exeFile.getAbsolutePath());
            } else {
                exeFile.delete();
            }
        }
        return flag;
    }

    /**
     * .
     */
    public static boolean copy(String srcFile, String destFile) {
        try {
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * .
     *
     * @param oldPath string ：c:/fqf.
     * @param newPath string ：f:/fqf/ff.
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
    }

    /**
     * .
     */
    public static boolean renameFile(String resFilePath, String newFilePath) {
        File resFile = new File(resFilePath);
        File newFile = new File(newFilePath);
        return resFile.renameTo(newFile);
    }

    /**
     * .
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static long getSDCardAvailaleSize() {
        File path = getRootPath();
        StatFs stat = new StatFs(path.getPath());
        long blockSize, availableBlocks;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    /**
     * .
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static long getDirSize(String path) {
        StatFs stat = new StatFs(path);
        long blockSize, availableBlocks;
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    /**
     * .
     */
    public static long getFileAllSize(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] childrens = file.listFiles();
                long size = 0;
                for (File f : childrens) {
                    size += getFileAllSize(f.getPath());
                }
                return size;
            } else {
                return file.length();
            }
        } else {
            return 0;
        }
    }

    /**
     * .
     */
    public static boolean initFile(String path) {
        boolean result = false;
        try {
            File file = new File(path);
            if (!file.exists()) {
                result = file.createNewFile();
            } else if (file.isDirectory()) {
                file.delete();
                result = file.createNewFile();
            } else if (file.exists()) {
                result = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * .
     */
    public static boolean initDirectory(String path) {
        boolean result = false;
        File file = new File(path);
        if (!file.exists()) {
            result = file.mkdir();
        } else if (!file.isDirectory()) {
            file.delete();
            result = file.mkdir();
        } else if (file.exists()) {
            result = true;
        }
        return result;
    }

    /**
     * .
     */
    public static void copyFile(File from, File to) throws IOException {
        if (!from.exists()) {
            throw new IOException("The source file not exist: " + from.getAbsolutePath());
        }
        FileInputStream fis = new FileInputStream(from);
        try {
            copyFile(fis, to);
        } finally {
            fis.close();
        }
    }

    /**
     * .
     */
    public static long copyFile(InputStream from, File to) throws IOException {
        long totalBytes = 0;
        FileOutputStream fos = new FileOutputStream(to, false);
        try {
            byte[] data = new byte[1024];
            int len;
            while ((len = from.read(data)) > -1) {
                fos.write(data, 0, len);
                totalBytes += len;
            }
            fos.flush();
        } finally {
            fos.close();
        }
        return totalBytes;
    }

    /**
     * .
     */
    public static void saveFile(InputStream inputStream, String filePath) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(filePath), false);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * UTF8.
     */
    public static void saveFileUTF8(String path, String content, Boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(path, append);
        Writer out = new OutputStreamWriter(fos, "UTF-8");
        out.write(content);
        out.flush();
        out.close();
        fos.flush();
        fos.close();
    }

    /**
     * UTF8.
     */
    public static String getFileUTF8(String path) {
        String result = "";
        InputStream fin = null;
        try {
            fin = new FileInputStream(path);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Intent.
     */
    public static Intent getFileIntent(String path, String mimeType) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), mimeType);
        return intent;
    }
}
