package com.example.sayone.movieapp;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by sayone on 2/2/17.
 */
public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES),"moviecache");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    /*public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }*/
    public File getFile(String url) throws IOException {
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        f.createNewFile();
        return f;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}
