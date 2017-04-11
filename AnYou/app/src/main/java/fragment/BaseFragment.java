package fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import activity.DeliveryListActivity;
import activity.LoginActivity;
import activity.TeachActivity;
import cn.bosyun.anyou.R;
import constant.Constant;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    View rootView;
    private PopupWindow popupWindow;
    private RelativeLayout iv_list;
    private TextView tv_title;
    private RelativeLayout iv_back;
    private View childView;
    private long currentTime;
    private String versionName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
            iv_back = (RelativeLayout) rootView.findViewById(R.id.iv_back);
            tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            iv_list = (RelativeLayout) rootView.findViewById(R.id.iv_list);
            FrameLayout childContainer = (FrameLayout) rootView.findViewById(R.id.fragment_base_child_container);

            iv_back.setOnClickListener(this);
            iv_list.setOnClickListener(this);

            int childLayout = getContentLayoutRes();
            childView = inflater.inflate(childLayout, childContainer);
            initPopupWindow();

        }
        return rootView;
    }

    @Override
    public void onResume() {
        initView(tv_title, childView);
        super.onResume();
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fg_detail_popupwindow, null);
        popupWindow = new PopupWindow(layout);
        //必须设置PopupWindow可以选中才能实现点击监听
        popupWindow.setFocusable(true);

        popupWindow.setWidth(230);
        popupWindow.setHeight(350);
        //必须设置PopupWindow的背景才能设置旁边可点击
        popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg_popupwindow));
        //点击旁边可以取消PopupWindow
        popupWindow.setOutsideTouchable(true);

        //对PopupWindow的取消进行监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        TextView clean = (TextView) layout.findViewById(R.id.clean);
        TextView print = (TextView) layout.findViewById(R.id.print);
        TextView set = (TextView) layout.findViewById(R.id.set);
        TextView teach = (TextView) layout.findViewById(R.id.teach);
        TextView about = (TextView) layout.findViewById(R.id.about);
        clean.setOnClickListener(this);
        print.setOnClickListener(this);
        set.setOnClickListener(this);
        teach.setOnClickListener(this);
        about.setOnClickListener(this);
    }

    protected abstract void initView(TextView tv_title, View childView);

    protected abstract int getContentLayoutRes();

    protected abstract void initData();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (System.currentTimeMillis() - currentTime > Constant.INTERVAL) {
                    CommonUtils.showToast(getActivity().getApplicationContext(), "再按一次退出应用");
                    currentTime = System.currentTimeMillis();
                } else {
                    getActivity().finish();
                }
                break;
            case R.id.iv_list:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    //弹出PopupWindow时界面变暗
                    popupWindow.showAsDropDown(iv_list, 0, 0);
                    WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                    params.alpha = 0.7f;
                    getActivity().getWindow().setAttributes(params);
                }
                break;
            //菜单里面的注销按钮
            case R.id.clean:
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                SPUtils.clear(getActivity());
                CommonUtils.toActivity(getActivity(), LoginActivity.class);
                CommonUtils.showToast(getActivity(), "注销成功");
                getActivity().finish();
                break;
            //菜单里面的出库单打印按钮
            case R.id.print:
                popupWindow.dismiss();
                CommonUtils.toActivity(getActivity(), DeliveryListActivity.class);
                break;
            //菜单里面的系统设置按钮
            case R.id.set:
                popupWindow.dismiss();
                CommonUtils.showToast(getActivity(), "设置功能待开发");
                break;
            //菜单里面的教程按钮
            case R.id.teach:
                popupWindow.dismiss();
                CommonUtils.toActivity(getActivity(), TeachActivity.class);
                Toast.makeText(getActivity(), "请左右滑动翻页查看!", Toast.LENGTH_LONG).show();
                break;
            //菜单里面的关于按钮
            case R.id.about:
                popupWindow.dismiss();
                //获取版本号
                try {
                    versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                new AlertDialog.Builder(getActivity()).setTitle("关于")//设置对话框标题
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
}
