package top.littlefogcat.tools.utils;

import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/**
 * 辅助功能工具类
 */
public class AccessibilityUtil {
    private static final String TAG = "AccessibilityUtil";

    /**
     * 检查开启辅助功能，需要权限{@link android.Manifest.permission#WRITE_SECURE_SETTINGS},
     * {@link android.Manifest.permission#WRITE_SETTINGS}
     */
    public static void checkAccessibilityService(Context context, Class serviceClass, OnEnableAccessibilityListener listener) {
        try {
            String enabledServicesSetting = Settings.Secure.getString(
                    context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

            ComponentName selfComponentName = new ComponentName(context.getPackageName(),
                    serviceClass.getName());
            String flattenToString = selfComponentName.flattenToString();

            Log.d(TAG, "checkAccessibilityService: \nenabled[" + enabledServicesSetting + "]\n"
                    + "flatten[" + flattenToString + "]");
            // 检查是否开启辅助功能，如果开启了就直接返回
            if (enabledServicesSetting == null ||
                    !enabledServicesSetting.equals(flattenToString)) {
                Log.d(TAG, "checkAccessibilityService: 未开启辅助功能");
            } else {
                Log.d(TAG, "checkAccessibilityService: 已经开启辅助功能");
                listener.onAlreadyEnable();
                return;
            }

            // 写入设置，开启辅助功能
            Settings.Secure.putString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                    flattenToString);
            Settings.Secure.putInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED, 1);

            enabledServicesSetting = Settings.Secure.getString(
                    context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (enabledServicesSetting != null && enabledServicesSetting.equals(flattenToString)) {
                // 开启成功
                listener.onSuccess();
            } else {
                // 开启失败
                listener.onFailed(new Throwable("开启辅助功能失败，请手动开启"));
            }
        } catch (Exception e) {
            Log.w(TAG, "开启辅助功能出错: " + e);
            listener.onFailed(new Throwable("开启辅助功能失败，请手动开启"));
        }
    }

    public interface OnEnableAccessibilityListener {
        void onSuccess();

        void onAlreadyEnable();

        void onFailed(Throwable t);
    }
}
