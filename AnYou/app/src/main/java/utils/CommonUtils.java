package utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
public class CommonUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());

    //得到new出来的handler
    public static Handler getHandler() {
        return handler;
    }

    //在主线程中执行的方法
    public static void runOnUIThrad(Runnable task) {
        handler.post(task);
    }

    private static Toast toast;

    //吐司
    public static void showToast(final Context context, final String text) {
        runOnUIThrad(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
                toast.setText(text);
                toast.show();
            }
        });
    }

    //跳转Activity
    public static void toActivity(Context context, Class aClass) {
        context.startActivity(new Intent(context, aClass));
    }

    //获取当前时间,年月日
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    // 数字型字符串千分位加逗号
    public static String addComma(String str) {

        boolean neg = false;
        if (str.startsWith("-")) {  //处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) { //处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }

    public static String touzi_ed_values22 = "";

    /**
     * 在数字型字符串千分位加逗号
     *
     * @param str
     * @param edtext
     * @return sb.toString()
     */
    public static String addCommas(String str, EditText edtext) {

        touzi_ed_values22 = edtext.getText().toString().trim().replaceAll(",", "");

        boolean neg = false;
        if (str.startsWith("-")) {  //处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) { //处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    //log写入本地
    public static void write(String s1, String s2) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
//        return formatter.format(curDate);
        String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Anyou";
        File file = new File(SDPath, "log.txt");
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(formatter.format(curDate) + s1 + s2 + "\n" + "======================================================================================================" + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
