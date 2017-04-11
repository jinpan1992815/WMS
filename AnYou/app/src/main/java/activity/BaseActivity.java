package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bosyun.anyou.R;
import constant.Constant;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/8 0008.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    private RelativeLayout iv_back;
    private RelativeLayout iv_list;
    private PopupWindow popupWindow;
    private FrameLayout container;
    private String versionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        iv_back = ((RelativeLayout) findViewById(R.id.base_iv_back));
        iv_list = ((RelativeLayout) findViewById(R.id.base_iv_list));
        iv_back.setOnClickListener(this);
        iv_list.setOnClickListener(this);
        initPopupWindow();

        container = ((FrameLayout) findViewById(R.id.activity_base_child_container));
        int childLayout = getContentLayoutRes();
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(childLayout, container);
        initView(view);
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.detail_popupwindow, null);
        popupWindow = new PopupWindow(layout);
        //必须设置PopupWindow可以选中才能实现点击监听
        popupWindow.setFocusable(true);
        popupWindow.setWidth(230);
        popupWindow.setHeight(220);
        //必须设置PopupWindow的背景才能设置旁边可点击
        popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg_popupwindow));
        //点击旁边可以取消PopupWindow
        popupWindow.setOutsideTouchable(true);
        //对PopupWindow的取消进行监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

        TextView clean = (TextView) layout.findViewById(R.id.clean);
        TextView set = (TextView) layout.findViewById(R.id.set);
        TextView about = (TextView) layout.findViewById(R.id.about);
        clean.setOnClickListener(this);
        set.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //左上角返回键
            case R.id.base_iv_back:
                finish();
                break;
            //右上角菜单键
            case R.id.base_iv_list:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    //弹出PopupWindow时界面变暗
                    popupWindow.showAsDropDown(iv_list, 0, 0);
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 0.7f;
                    getWindow().setAttributes(params);
                }
                break;
            //菜单列表里面的注销按钮
            case R.id.clean:
                SharedPreferences.Editor editor = getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                SPUtils.clear(this);
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                CommonUtils.showToast(this, "注销成功");
                break;
            //菜单里面的设置按钮
            case R.id.set:
                CommonUtils.showToast(this, "设置功能待开发");
                break;
            //菜单里面的关于按钮
            case R.id.about:
                popupWindow.dismiss();
                //获取版本号
                try {
                    versionName = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                new AlertDialog.Builder(BaseActivity.this).setTitle("关于")//设置对话框标题
                        .setMessage("当前版本:" + versionName + "\n" + "更新时间:" + Constant.time)//设置显示的内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
    }

    public abstract int getContentLayoutRes();

    public abstract void initView(View v);
}
