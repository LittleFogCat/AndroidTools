package top.littlefogcat.tools;

import android.content.Context;
import android.os.Environment;
import android.os.Process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by jjy on 2017/12/21.
 */

public class MyCrashHandler implements Thread.UncaughtExceptionHandler {
    private String mPackageName;

    public MyCrashHandler(Context context) {
        mPackageName = context.getPackageName();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            String filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mPackageName
                    + File.separator + "crash" + System.currentTimeMillis() + ".txt";
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();

            PrintWriter pw = new PrintWriter(new FileWriter(file));
            e.printStackTrace(pw);

            while (e.getCause() != null) {
                e.getCause().printStackTrace(pw);
                e = e.getCause();
            }

            pw.flush();
            pw.close();

            Process.killProcess(Process.myPid());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
