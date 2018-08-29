package top.littlefogcat.tools.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: LittleFogCat
 * Email: littlefogcat@foxmail.com
 * Created at: 2018/8/29
 */
public class RxHelper {
    public static <T> ObservableTransformer<T, T> ioUi() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
