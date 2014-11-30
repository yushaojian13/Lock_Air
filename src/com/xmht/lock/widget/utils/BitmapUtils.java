
package com.xmht.lock.widget.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class BitmapUtils {

    /**
     * call in a work thread
     */
    public static void saveNextBitmap(Context context, Bitmap bitmap, String fileName) {
        OutputStream os = null;
        try {
            os = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(CompressFormat.PNG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * call in a work thread
     * 
     * @throws IOException
     */
    public static Bitmap loadCurBitmap(Context context, String fileName) throws IOException {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        fis = context.openFileInput(fileName);
        bitmap = BitmapFactory.decodeStream(fis);
        if (fis != null) {
            fis.close();
        }

        return bitmap;
    }
}
