package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import adapter.SellFragmentAdapter;
import bean.GetOpenPOList;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class SellFragment extends BaseFragment {

    private ImageView sm3;
    private EditText search3;
    private ListView listView;
    private SellFragmentAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<GetOpenPOList> list = (List<GetOpenPOList>) msg.obj;
            adapter = new SellFragmentAdapter(getActivity(), list);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };
    private ImageView refresh;

    @Override
    protected void initView(TextView tv_title, View childView) {
        initData();
        tv_title.setText("销售出库");
        tv_title.setVisibility(View.VISIBLE);
        listView = (ListView) childView.findViewById(R.id.GetOpenSOList_lv);

        search3 = ((EditText) childView.findViewById(R.id.search3));
        sm3 = ((ImageView) childView.findViewById(R.id.iv_sm3));
        refresh = ((ImageView) childView.findViewById(R.id.iv_refresh));

        //搜索框点击搜索
        search3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //reJson={"UserName":"testAccount","Password":"1234","DBName":"ANSA410","Keyword":"62"}
                SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                final String username = sp.getString("username", null);
                final String password = sp.getString("password", null);
                final String dbname = SPUtils.getString(getActivity().getApplicationContext(), "DBName");
                Editable searchText = search3.getText();
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"Keyword\":\"" + searchText + "\"}";

                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(getActivity(), "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetOpenPOList>>() {
                        }.getType();
                        try {
                            Object list = new Gson().fromJson(result, listType);
                            Message message = Message.obtain();
                            message.obj = list;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                        }
                    }
                };
                HttpUtils.getResult(Constant.HOST_SearchOpenSOList, string, searchCallBack);
                return false;
            }
        });

        //扫描二维码
        sm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objectAuth_15 = SPUtils.getString(getActivity(), "ObjectAuth_15");
                if (objectAuth_15.equals("F") || objectAuth_15.equals("R")) {
                    getActivity().startActivityForResult(new Intent(getActivity().getBaseContext(), CaptureActivity.class), 3);
                } else if (objectAuth_15.equals("N")) {
                    CommonUtils.showToast(getActivity(), "没有权限");
                }
            }
        });

        //刷新按钮
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                adapter.notifyDataSetChanged();
                CommonUtils.showToast(getActivity(), "刷新成功");
            }
        });
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_sell;
    }

    @Override
    protected void initData() {
        //reJson={"UserName":"testAccount","Password":"1234","DBName":"ANSA410"}
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String username = sp.getString("username", null);
        final String password = sp.getString("password", null);
        final String dbname = SPUtils.getString(getActivity().getApplicationContext(), "DBName");
        final String json = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\"}";
        Callback callBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Type listType = new TypeToken<List<GetOpenPOList>>() {
                }.getType();
                try {
                    Object list = new Gson().fromJson(result, listType);
                    Message message = Message.obtain();
                    message.obj = list;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }
            }
        };
        HttpUtils.getResult(Constant.HOST_GetOpenSOList, json, callBack);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            initData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SPUtils.delete(getActivity(), "sm3");
    }
}
