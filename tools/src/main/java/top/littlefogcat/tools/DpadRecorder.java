package top.littlefogcat.tools;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjy on 2018/5/31.
 * <p>
 * 键盘输入记录器
 */

public class DpadRecorder {

    private static final String TAG = "DpadRecorder";

    private static DpadRecorder sRecorder;

    private Map<String, Callback> mCallbackMap = new HashMap<>();
    private int mMaxLen;

    private StringBuilder mLastKeys = new StringBuilder();

    private DpadRecorder() {
        Log.d(TAG, "DpadRecorder: constructor");
    }

    public static DpadRecorder getInstance() {
        if (sRecorder == null) {
            sRecorder = new DpadRecorder();
        }
        return sRecorder;
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                mLastKeys.append(keyCode - KeyEvent.KEYCODE_0);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                mLastKeys.append("u");
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mLastKeys.append("l");
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mLastKeys.append("d");
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mLastKeys.append("r");
                break;
            default:
                if (mLastKeys.length() != 0) {
                    mLastKeys.setLength(0);
                }
                break;
        }
        if (mLastKeys.length() > mMaxLen) {
            mLastKeys.deleteCharAt(0);
        }
        Log.v(TAG, "onKeyDown: " + mLastKeys.toString());
        checkFormat();
    }

    private void checkFormat() {
        int len = mLastKeys.length();
        if (len == 0) {
            return;
        }
        for (String s : mCallbackMap.keySet()) {
            int sLen = s.length();
            if (sLen > len || sLen == 0) {
                continue;
            }
            String sub = mLastKeys.substring(len - sLen);
            if (sub.equals(s)) {
                Callback callback = mCallbackMap.get(s);
                callback.onCallback();
                mLastKeys.setLength(0);
                return;
            }
        }
    }

    public void addCallback(String format, Callback callback) {
        if (format.length() > 0 && callback != null) {
            mCallbackMap.put(format, callback);
            if (format.length() > mMaxLen) {
                mMaxLen = format.length();
            }
        }
    }

    public interface Callback {
        void onCallback();
    }

}
