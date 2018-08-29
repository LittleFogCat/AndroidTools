package top.littlefogcat.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jjy on 2018/1/17.
 */

public class SpUtils {
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 将值存入到Preference中。
     *
     * @param key the key
     * @param val the value (ps: 不可以为Set<String>)
     */
    public static void put(String key, Object val) {
        if (key == null || val == null) {
            throw new NullPointerException("key or val is null");
        }
        SharedPreferences.Editor editor = sp.edit();
        if (val instanceof Integer) {
            editor.putInt(key, (Integer) val);
        } else if (val instanceof String) {
            editor.putString(key, (String) val);
        } else if (val instanceof Boolean) {
            editor.putBoolean(key, (Boolean) val);
        } else if (val instanceof Float) {
            editor.putFloat(key, (Float) val);
        } else if (val instanceof Long) {
            editor.putLong(key, (Long) val);
        }
        editor.apply();
    }

    public static Object get(String key, Object defVal) {
        if (key == null) {
            throw new NullPointerException("key is Null");
        }
        if (defVal instanceof Integer) {
            return sp.getInt(key, (Integer) defVal);
        } else if (defVal instanceof String) {
            return sp.getString(key, (String) defVal);
        } else if (defVal instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defVal);
        } else if (defVal instanceof Float) {
            return sp.getFloat(key, (Float) defVal);
        } else if (defVal instanceof Long) {
            return sp.getLong(key, (Long) defVal);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + defVal);
        }
    }

    public static void delete(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

}
