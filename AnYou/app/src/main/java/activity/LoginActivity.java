package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bean.User;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/5 0005.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private TextView login_tv_2;
    private CheckBox dl_cb;
    private Button dl_bt;
    private SharedPreferences sp;
    private String usernameValue;
    private String passwordValue;
    private ProgressDialog progressDialog;
    private boolean eyeOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PgyUpdateManager.register(LoginActivity.this, "",
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {

                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("更新")
                                .setMessage("发现新版本,请点击更新!")
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        LoginActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        init();
        progressDialog = ProgressDialog.show(LoginActivity.this, "", "正在登陆……");
        progressDialog.dismiss();
    }

    //界面初始化
    private void init() {
        dl_bt = ((Button) findViewById(R.id.dl_bt));
        dl_cb = ((CheckBox) findViewById(R.id.dl_cb));
        username = ((EditText) findViewById(R.id.et_username));
        password = ((EditText) findViewById(R.id.et_password));
        login_tv_2 = ((TextView) findViewById(R.id.login_tv_2));

        if (sp.getBoolean("checkboxBoolean", false)) {
            username.setText(sp.getString("username", null));
            password.setText(sp.getString("password", null));
            dl_cb.setChecked(true);

        }
        dl_bt.setOnClickListener(this);
        login_tv_2.setOnClickListener(this);
        password.setOnEditorActionListener(EditorActionListener());
        username.setSelection(username.getText().length());
        password.setSelection(password.getText().length());
        password.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = password.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > password.getWidth()
                        - password.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    if (eyeOpen) {
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        password.setSelection(password.getText().length());
                        eyeOpen = false;
                    } else {
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setSelection(password.getText().length());
                        eyeOpen = true;
                    }
                }
                return false;
            }
        });

    }

    //软键盘进入建实现登陆
    private TextView.OnEditorActionListener EditorActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    //等待动画
                    progressDialog.show();
                    login();
                }
                return false;
            }
        };
    }

    //登陆方法
    private void login() {
        usernameValue = username.getText().toString();
        passwordValue = password.getText().toString();
        //判断记住密码多选框的状态
        if (usernameValue.trim().equals("")) {
            CommonUtils.showToast(this, "请输入用户名");
            return;
        }
        if (passwordValue.trim().equals("")) {
            CommonUtils.showToast(this, "请输入密码");
            return;
        }
        if (!usernameValue.trim().equals("") && !passwordValue.trim().equals("")) {
            //等待动画
            progressDialog.show();
        }
        //存储用户名和密码到SharedPreferences
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", usernameValue);
        editor.putString("password", passwordValue);

        //记录勾选记住密码框的状态
        boolean CheckBoxLogin = dl_cb.isChecked();
        if (CheckBoxLogin) {
            editor.putBoolean("checkboxBoolean", true);
            editor.commit();
        } else {
            editor.putBoolean("checkboxBoolean", false);
            editor.commit();
        }

        Map<String, String> map = new HashMap<>();
        map.put("UserName", usernameValue);
        map.put("Password", passwordValue);
        String json = new Gson().toJson(map);

        //联网请求服务器
        Callback callBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                progressDialog.dismiss();
                CommonUtils.showToast(LoginActivity.this, "连接失败,请检查网络" + Constant.HOST_BASE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                User user = new Gson().fromJson(result, User.class);
                String status = HttpUtils.getCode(result, "status");
                if ("0".equals(status)) {
                    progressDialog.dismiss();
                    //用户名和密码正确
                    SPUtils.put(getApplicationContext(), "DBName", user.getDBName());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_22", user.getObjectAuth_22());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_22", user.getObjectPriceAuth_22());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_22", user.getObjectAmountAuth_22());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_15", user.getObjectAuth_15());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_15", user.getObjectPriceAuth_15());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_15", user.getObjectAmountAuth_15());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_590", user.getObjectAuth_590());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_590", user.getObjectPriceAuth_590());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_590", user.getObjectAmountAuth_590());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_67", user.getObjectAuth_67());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_67", user.getObjectPriceAuth_67());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_67", user.getObjectAmountAuth_67());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_21", user.getObjectAuth_21());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_21", user.getObjectPriceAuth_21());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_21", user.getObjectAmountAuth_21());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_16", user.getObjectAuth_16());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_16", user.getObjectPriceAuth_16());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_16", user.getObjectAmountAuth_16());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_591", user.getObjectAuth_591());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_591", user.getObjectPriceAuth_591());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_591", user.getObjectAmountAuth_591());
                    SPUtils.put(getApplicationContext(), "ObjectAuth_601", user.getObjectAuth_601());
                    SPUtils.put(getApplicationContext(), "ObjectPriceAuth_601", user.getObjectPriceAuth_601());
                    SPUtils.put(getApplicationContext(), "ObjectAmountAuth_601", user.getObjectAmountAuth_601());

                    CommonUtils.showToast(getApplicationContext(), "登陆成功");
                    String shortName = HttpUtils.getCode(result, "ShortName");
                    String userName = HttpUtils.getCode(result, "UserName");
                    Intent intent = new Intent();
                    intent.putExtra("ShortName", shortName);
                    intent.putExtra("UserName", userName);
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if ("-1".equals(status)) {
                    progressDialog.dismiss();
                    //用户名或者密码错误
                    CommonUtils.showToast(LoginActivity.this, "用户名或密码有误");
                }
            }
        };

        HttpUtils.getResult(Constant.HOST_SAPB1Login, json, callBack);
    }

    //设置时间间隔,防止登陆按钮多次点击
    private static final int MIN_CLICK_DELAY_TIME = 10000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    //子控件的OnClickListener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_tv_2:
                CommonUtils.showToast(LoginActivity.this, "请联系管理员 !");
                break;
            case R.id.dl_bt:
                if (isFastClick()) {
                    login();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PgyUpdateManager.unregister();
    }
}
