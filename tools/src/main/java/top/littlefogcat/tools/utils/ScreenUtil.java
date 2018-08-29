package top.littlefogcat.tools.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by jjy on 2018/6/6.
 * <p>
 * 自动适配屏幕像素的工具类
 */

public class ScreenUtil {
    public static final int DEFAULT_WIDTH = 1920;
    public static final int DEFAULT_HEIGHT = 1080;

    private static int sScreenWidth;
    private static int sScreenHeight;
    private static int sDesignWidth;
    private static int sDesignHeight;

    /**
     * @param designW 设计图基准的宽度，如1920
     * @param designH 设计图基准的高度，如1080
     */
    public static void init(Activity activity, int designW, int designH) {
        sDesignWidth = designW;
        sDesignHeight = designH;

        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics m = new DisplayMetrics();
        display.getMetrics(m);

        sScreenWidth = m.widthPixels;
        sScreenHeight = m.heightPixels;

        if (sScreenWidth == 0 || sScreenHeight == 0) {
            sScreenWidth = designW;
            sScreenHeight = designH;
        }
    }

    /**
     * 根据屏幕分辨率自适应宽度
     *
     * @param origin 设计图中的宽度，像素
     * @return 实际屏幕中的宽度，像素
     */
    public static int autoWidth(int origin) {
        int autoSize = origin * sScreenWidth / sDesignWidth;
        if (origin != 0 && autoSize == 0) {
            return 1;
        }
        return autoSize;
    }

    /**
     * 根据屏幕分辨率自适应高度
     *
     * @param origin 设计图中的高度，像素
     * @return 实际屏幕中的高度，像素
     */
    public static int autoHeight(int origin) {
        int autoSize = origin * sScreenHeight / sDesignHeight;
        if (origin != 0 && autoSize == 0) {
            return 1;
        }
        return autoSize;
    }

    public static int getScreenWidth() {
        return sScreenWidth;
    }

    public static void setScreenWidth(int w) {
        sScreenWidth = w;
    }

    public static int getScreenHeight() {
        return sScreenHeight;
    }

    public static void setScreenHeight(int h) {
        sScreenHeight = h;
    }
}
