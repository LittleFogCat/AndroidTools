package top.littlefogcat.tools.utils;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: LittleFogCat
 * Email: littlefogcat@foxmail.com
 * Created at: 2018/8/29
 */
@SuppressWarnings("ConstantConditions")
public class OkHttpUtils {
    private static volatile OkHttpUtils instance;
    private static final Object instanceLock = new Object();
    private OkHttpClient mClient;

    private OkHttpUtils(OkHttpClient client) {
        if (client != null) {
            mClient = client;
        } else {
            mClient = new OkHttpClient();
        }
    }

    public static OkHttpUtils getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                instance = new OkHttpUtils(null);
            }
        }
        return instance;
    }

    public void get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(callback);
    }

    public String get(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = mClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void post(String url, String json, Callback callback) {
        Request request = new Request.Builder().url(url).post(jsonBody(json)).build();
        mClient.newCall(request).enqueue(callback);
    }

    public String post(String url, String json) {
        Request request = new Request.Builder().url(url).post(jsonBody(json)).build();
        try {
            Response response = mClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static RequestBody jsonBody(String json) {
        if (json == null) {
            json = "{}";
        }
        return RequestBody.create(MediaType.parse("json/application"), json);
    }
}
