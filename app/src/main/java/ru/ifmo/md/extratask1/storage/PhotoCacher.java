package ru.ifmo.md.extratask1.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pinguinson on 17.01.2015.
 */
public class PhotoCacher {
    private String cacheDir;

    public PhotoCacher(Context ctx) {
        cacheDir = ctx.getCacheDir().getAbsolutePath();
    }

    public boolean isAvailable(String res) {
        return getFile(res).exists();
    }

    public void set(String res, InputStream is) {
        File file = getFile(res);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putToImageView(ImageView imgView, String res) {
        try {
            String filename = getFile(res).getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(filename);
            imgView.setImageBitmap(bitmap);
        } catch (OutOfMemoryError e) {
            Log.e("PhotoCacher failed to load cached image (probably because of using virtual device) from: ", getFile(res).getAbsolutePath());
        }
    }

    private File getFile(String res) {
        return new File(cacheDir + File.separator + res);
    }
}
