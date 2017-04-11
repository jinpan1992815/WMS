package activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import bean.DownloadDeliveryPDF;
import bean.GetOpenDeliveryList;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2017/1/23 0023.
 */
public class DeliveryListActivity extends BaseActivity {
    private EditText search;
    private ImageView sm;
    private ListView lv;
    private MyAdapter myAdapter;
    private List<GetOpenDeliveryList> list;
    private String username;
    private String password;
    private String dbName;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_deliverylist;
    }

    @Override
    public void initView(View v) {
        search = ((EditText) findViewById(R.id.search));
        sm = ((ImageView) findViewById(R.id.iv_sm));
        lv = ((ListView) findViewById(R.id.DeliveryList_lv));

        initData();
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);

        //搜索框点击搜索
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //reJson={"UserName":"testAccount","Password":"1234","DBName":"ANSA410","Keyword":"407"}
                Editable searchText = search.getText();
                String string = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbName + "\",\"Keyword\":\"" + searchText + "\"}";

                Callback searchCallBack = new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        CommonUtils.showToast(getApplicationContext(), "请检查网络");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Type listType = new TypeToken<List<GetOpenDeliveryList>>() {
                        }.getType();
                        list = new Gson().fromJson(result, listType);
                        Message message = Message.obtain();
                        message.obj = list;
                        handler.sendMessage(message);
                    }
                };
                HttpUtils.getResult(Constant.HOST_searchOpenDeliveryList, string, searchCallBack);
                return false;
            }
        });

        //扫描二维码
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryListActivity.this.startActivityForResult(new Intent(DeliveryListActivity.this.getBaseContext(), CaptureActivity.class), 8);
            }
        });

    }

    //请求数据
    private void initData() {
        //reJson={"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410"}
        SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        username = sp.getString("username", null);
        password = sp.getString("password", null);
        dbName = SPUtils.getString(getApplicationContext(), "DBName");
        String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbName + "\"}";
        Callback callBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                Type listType = new TypeToken<List<GetOpenDeliveryList>>() {
                }.getType();
                list = new Gson().fromJson(result, listType);
                Message message = Message.obtain();
                message.obj = list;
                handler.sendMessage(message);
            }
        };
        HttpUtils.getResult(Constant.HOST_getOpenDeliveryList, json, callBack);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            final GetOpenDeliveryList getOpenDeliveryList = list.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(DeliveryListActivity.this, R.layout.ac_item5_3, null);
                holder.item1 = ((TextView) convertView.findViewById(R.id.item1));
                holder.item2 = ((TextView) convertView.findViewById(R.id.item2));
                holder.item3 = ((TextView) convertView.findViewById(R.id.item3));
                holder.item4 = (Button) convertView.findViewById(R.id.item4);
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }
            holder.item4.setTag(position);

            holder.item1.setText(getOpenDeliveryList.getDocDate());
            holder.item2.setText(getOpenDeliveryList.getDocNum());
            holder.item3.setText(getOpenDeliveryList.getCardName());
            holder.item4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog progressDialog = ProgressDialog.show(DeliveryListActivity.this, "", "正在下载,请稍后...");
                    //{"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410","DocEntry":208}
                    int docEntry = getOpenDeliveryList.getDocEntry();
                    String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbName + "\", \"DocEntry\":" + docEntry + "}";
                    Callback callBack = new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            progressDialog.dismiss();
                            CommonUtils.showToast(getApplicationContext(), "下载失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Anyou";
                            String result = response.body().string();
                            DownloadDeliveryPDF pdf = new Gson().fromJson(result, DownloadDeliveryPDF.class);
                            byte[] bytes = pdf.getFilebyte();
                            final File file = new File(SDPath, "单据.pdf");
                            FileOutputStream outputStream = new FileOutputStream(file);
                            outputStream.write(bytes, 0, bytes.length);
                            outputStream.close();
                            progressDialog.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        CommonUtils.showToast(getApplicationContext(), "下载完成,正在打开...");
                                        Thread.sleep(3000);
                                        openFile(getApplicationContext(), file);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    };
                    HttpUtils.getResult(Constant.HOST_DownloadDeliveryPDF, json, callBack);
                }
            });
            return convertView;
        }

        class ViewHolder {

            TextView item1;
            TextView item2;
            TextView item3;
            Button item4;
        }
    }

    //扫描二维码的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            if (requestCode == 8) {
                search.setText(result);
                ImageView iv_sm = (ImageView) findViewById(R.id.iv_sm);
                InputMethodManager imm = (InputMethodManager) iv_sm.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    /**
     * 调用系统应用打开文件
     *
     * @param context
     * @param file
     */
    public static void openFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        //跳转
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            CommonUtils.showToast(context, "找不到打开此文件的应用！");
        }
    }

    /***
     * 根据文件后缀回去MIME类型
     ****/

    private static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".pdf", "application/pdf"}};
}
