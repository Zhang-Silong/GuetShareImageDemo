package com.example.guetshareimagedemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ZhangSilong on 2021/12/27.
 */
public class BitmapUtils {

    /**
     * 将bitmap转为base64
     */
    public static String bitmapToBase64(Bitmap bitmap){
        String result = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            if (bitmap != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                result = Base64.encodeToString(bytes, Base64.DEFAULT);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     */
    public static Bitmap base64ToBitmap(String base64){
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
