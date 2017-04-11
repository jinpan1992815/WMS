package activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bean.GetInformedPOList;
import bean.GetOpenInvTransRequestList;
import bean.GetOpenPOList;
import bean.GetOpenProducitonOrderList;
import cn.bosyun.anyou.R;
import constant.Constant;
import fragment.BuyFragment;
import fragment.MakeFragment;
import fragment.OtherFragment;
import fragment.SaveFragment;
import fragment.SellFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

public class MainActivity extends Activity {
    private RadioGroup radioGroup;
    List<Fragment> fragments = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private long currentTime;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Anyou");
        if (!file.exists()) {
            file.mkdirs();
        }
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String shortName = intent.getStringExtra("ShortName");
        String userName = intent.getStringExtra("UserName");

        radioGroup = ((RadioGroup) findViewById(R.id.main_rg));
        tv1 = (TextView) findViewById(R.id.main_tv1);
        tv2 = (TextView) findViewById(R.id.main_tv2);
        tv1.setText(shortName);
        tv2.setText(userName);

        //给MainActivity添加fragment
        fragments.add(new BuyFragment());
        fragments.add(new SellFragment());
        fragments.add(new MakeFragment());
        fragments.add(new SaveFragment());
        fragments.add(new OtherFragment());
        radioGroup.setOnCheckedChangeListener(checkListener);
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

    }

    //把Fragment绑定到radiobutton上
    private RadioGroup.OnCheckedChangeListener checkListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            View child = group.findViewById(checkedId);
            int index = group.indexOfChild(child);
            Fragment fragment = fragments.get(index);
            addShowHideFragment(fragment);
//            replaceFragment(fragment);
        }
    };

//    private void replaceFragment(Fragment fragment) {
//
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.main_fragment_container, fragment)
//                .commit();
//    }


    Fragment currentFragment;
    List<Fragment> hasAddedFragment = new ArrayList<>();

    //add+show 搭建主框架
    public void addShowHideFragment(Fragment fragment) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }
        if (hasAddedFragment.contains(fragment)) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.main_fragment_container, fragment);
            hasAddedFragment.add(fragment);
        }
        fragmentTransaction.commit();
        currentFragment = fragment;

    }

    //系统返回按钮方法重写
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.dispatchKeyEvent(event);

    }

    private void exit() {
        if (System.currentTimeMillis() - currentTime > Constant.INTERVAL) {
            CommonUtils.showToast(MainActivity.this, "再按一次退出应用");
            currentTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    //扫描二维码的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");

            SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String username = sp.getString("username", null);
            String password = sp.getString("password", null);
            String dbname = SPUtils.getString(getApplicationContext(), "DBName");
            if (requestCode == 1) {
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"Keyword\":\"" + result + "\"}";
                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(getApplicationContext(), "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetOpenPOList>>() {
                        }.getType();
                        List<GetOpenPOList> list = new Gson().fromJson(result, listType);
                        if (list.size() != 0) {
                            int docEntry = list.get(0).getDocEntry();
                            Intent intent = new Intent(getApplicationContext(), BuyActivity.class);
                            intent.putExtra("docEntry", String.valueOf(docEntry));
                            intent.putExtra("url", Constant.HOST_GetOpenPODetail);
                            intent.putExtra("urlAdd", Constant.HOST_AddGRPOBasedonPO);
                            startActivity(intent);
                        } else {
                            CommonUtils.showToast(getApplicationContext(), "没有查询到结果");
                        }
                    }
                };
                HttpUtils.getResult(Constant.HOST_SearchOpenPOList, string, searchCallBack);

                EditText search1 = (EditText) findViewById(R.id.search1);
                InputMethodManager imm = (InputMethodManager) search1.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
            if (requestCode == 2) {
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"Keyword\":\"" + result + "\"}";
                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(getApplicationContext(), "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetInformedPOList>>() {
                        }.getType();
                        List<GetInformedPOList> list = new Gson().fromJson(result, listType);
                        if (list.size() != 0) {
                            int docEntry = list.get(0).getDocEntry();
                            Intent intent = new Intent(getApplicationContext(), BuyActivity.class);
                            intent.putExtra("docEntry", String.valueOf(docEntry));
                            intent.putExtra("url", Constant.HOST_GetInformedPODetail);
                            intent.putExtra("urlAdd", Constant.HOST_AddGRPOBasedonInformedPO);
                            startActivity(intent);
                        } else {
                            CommonUtils.showToast(getApplicationContext(), "没有查询到结果");
                        }
                    }
                };
                HttpUtils.getResult(Constant.HOST_SearchInformedPOList, string, searchCallBack);

                EditText search2 = (EditText) findViewById(R.id.search2);
                InputMethodManager imm = (InputMethodManager) search2.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
            if (requestCode == 3) {
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"Keyword\":\"" + result + "\"}";
                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(getApplicationContext(), "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetOpenPOList>>() {
                        }.getType();
                        List<GetOpenPOList> list = new Gson().fromJson(result, listType);
                        if (list.size() != 0) {
                            int docEntry = list.get(0).getDocEntry();
                            Intent intent = new Intent(getApplicationContext(), SellActivity.class);
                            intent.putExtra("docEntry", String.valueOf(docEntry));
                            startActivity(intent);
                        } else {
                            CommonUtils.showToast(getApplicationContext(), "没有查询到结果");
                        }
                    }
                };
                HttpUtils.getResult(Constant.HOST_SearchOpenSOList, string, searchCallBack);
                EditText search3 = (EditText) findViewById(R.id.search3);
                InputMethodManager imm = (InputMethodManager) search3.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
            if (requestCode == 4) {
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"Keyword\":\"" + result + "\"}";
                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(getApplicationContext(), "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetOpenProducitonOrderList>>() {
                        }.getType();
                        List<GetOpenProducitonOrderList> list = new Gson().fromJson(result, listType);
                        if (list.size() != 0) {
                            int docEntry = list.get(0).getDocEntry();
                            Intent intent = new Intent(getApplicationContext(), MakeActivity.class);
                            intent.putExtra("docEntry", String.valueOf(docEntry));
                            startActivity(intent);
                        } else {
                            CommonUtils.showToast(getApplicationContext(), "没有查询到结果");
                        }
                    }
                };
                HttpUtils.getResult(Constant.HOST_SearchOpenProductionOrderList, string, searchCallBack);

                EditText search4 = (EditText) findViewById(R.id.search4);
                InputMethodManager imm = (InputMethodManager) search4.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
            if (requestCode == 5) {
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"Keyword\":\"" + result + "\"}";
                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(getApplicationContext(), "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetOpenInvTransRequestList>>() {
                        }.getType();
                        List<GetOpenInvTransRequestList> list = new Gson().fromJson(result, listType);
                        if (list.size() != 0) {
                            int docEntry = list.get(0).getDocEntry();
                            Intent intent = new Intent(getApplicationContext(), SaveActivity2.class);
                            intent.putExtra("docEntry", String.valueOf(docEntry));
                            startActivity(intent);
                        } else {
                            CommonUtils.showToast(getApplicationContext(), "没有查询到结果");
                        }
                    }
                };
                HttpUtils.getResult(Constant.HOST_SearchOpenInvTransRequestList, string, searchCallBack);

                EditText search5 = (EditText) findViewById(R.id.search5);
                InputMethodManager imm = (InputMethodManager) search5.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }

        }
    }
}
