package pager;

import android.app.Fragment;
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

import activity.MainActivity;
import adapter.GetOpenPOListPagerAdapter;
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
 * Created by Administrator on 2016/12/19 0019.
 */
public class GetOpenPOListPager extends Fragment {

    private final EditText search1;
    private final ImageView sm1;
    private final ImageView refresh;
    private ListView listview;
    View rootView;
    Context context;
    private GetOpenPOListPagerAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<GetOpenPOList> list = (List<GetOpenPOList>) msg.obj;
            adapter = new GetOpenPOListPagerAdapter(context, list);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };

    public GetOpenPOListPager(final Context context) {
        //reJson={"UserName":"testAccount","Password":"1234","DBName":"ANSA410"}
        this.context = context;
        initData();

        rootView = View.inflate(context, R.layout.pager_getopenpolist, null);
        search1 = ((EditText) rootView.findViewById(R.id.search1));
        sm1 = ((ImageView) rootView.findViewById(R.id.iv_sm1));
        refresh = ((ImageView) rootView.findViewById(R.id.iv_refresh));
        listview = ((ListView) rootView.findViewById(R.id.GetOpenPOListPager_lv));

        //搜索框点击搜索
        search1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //reJson={"UserName":"testAccount","Password":"1234","DBName":"ANSA410","Keyword":"62"}
                Editable searchText = search1.getText();
                SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                final String username = sp.getString("username", null);
                final String password = sp.getString("password", null);
                final String dbname = SPUtils.getString(context, "DBName");
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"Keyword\":\"" + searchText + "\"}";

                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(context, "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetOpenPOList>>() {
                        }.getType();
                        Object list = new Gson().fromJson(result, listType);
                        Message message = Message.obtain();
                        message.obj = list;
                        handler.sendMessage(message);
                    }
                };
                HttpUtils.getResult(Constant.HOST_SearchOpenPOList, string, searchCallBack);
                return false;
            }
        });

        //扫描二维码
        sm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objectAuth_22 = SPUtils.getString(context, "ObjectAuth_22");
                if (objectAuth_22.equals("F") || objectAuth_22.equals("R")) {
                    ((MainActivity) context).startActivityForResult(new Intent(context, CaptureActivity.class), 1);
                } else if (objectAuth_22.equals("N")) {
                    CommonUtils.showToast(context, "没有权限");
                }
            }
        });

        //刷新按钮
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                CommonUtils.showToast(context, "刷新成功");
            }
        });
    }

    private void initData() {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String username = sp.getString("username", null);
        final String password = sp.getString("password", null);
        final String dbname = SPUtils.getString(context, "DBName");
        final String json = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\"}";
        Callback callBack = new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                CommonUtils.showToast(context, "请检查网络");
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

        HttpUtils.getResult(Constant.HOST_GetOpenPOList, json, callBack);
    }

    public View getView() {
        return rootView;
    }

}
