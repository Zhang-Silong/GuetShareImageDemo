package com.example.guetshareimagedemo.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by ZhangSilong on 2021/12/26.
 */
public class SizeUtils {

    public static Point getScreenSize(Context context){
        Point point = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        return point;
    }

}
