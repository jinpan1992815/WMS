package activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import bean.GetOpenPODetail;
import cn.bosyun.anyou.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/7 0007.
 */
public class BuyActivity extends BaseActivity {

    private TextView buy_tv1;
    private TextView buy_tv2;
    private TextView tv_time;
    private TextView buy_tv3;
    private TextView buy_tv4;
    private TextView buy_tv5;
    private ListView buy_lv;
    private EditText buy_bz;
    private Button bt_add;
    private GetOpenPODetail getOpenPODetail;
    private List<GetOpenPODetail.Linesbean> lines;
    private MyAdapter myAdapter;
    private ProgressDialog progressDialog;
    private View item_foot;
    private String objectAuth_22;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            lines = getOpenPODetail.getLines();
            myAdapter.notifyDataSetChanged();

            buy_tv1.setText(getOpenPODetail.getCardCode());
            buy_tv2.setText(getOpenPODetail.getCardName());
        }
    };

    //默认显示当天时间
    private void initTime() {
        String format = CommonUtils.getCurrentTime();
        tv_time.setText(format);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_add: //点击添加按钮的监听
                    new AlertDialog.Builder(BuyActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("是否添加采购入库单?")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //等待动画
                            progressDialog = ProgressDialog.show(BuyActivity.this, "", "加载中，请稍后……");

                            //连接服务器获取数据
                            SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            final String username = sp.getString("username", null);
                            final String password = sp.getString("password", null);
                            final String dbname = SPUtils.getString(getApplicationContext(), "DBName");
                            String s = new Gson().toJson(getOpenPODetail.getLines());
                            String DocDate = tv_time.getText().toString();
                            String CardCode = buy_tv1.getText().toString();
                            String CardName = buy_tv2.getText().toString();
                            String Comments = buy_bz.getText().toString().trim();
                            Intent intent = getIntent();
                            String docEntry = intent.getStringExtra("docEntry");
                            String urlAdd = intent.getStringExtra("urlAdd");
                            final String json = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"CardCode\":\"" + CardCode + "\"," +
                                    "\"CardName\":\"" + CardName + "\",\"DocDate\":\"" + DocDate + "\",\"DocEntry\":" + docEntry + "," + "\"Comments\":\"" + Comments + "\"" + "," +
                                    "\"Lines\":" + s + "}";
                            Callback callBack = new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    progressDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(BuyActivity.this).setTitle("添加失败:").setMessage("没有连接到服务器").setNegativeButton("取消", null).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    String result = response.body().string();
                                    String status = HttpUtils.getCode(result, "status");
                                    final String Description = HttpUtils.getCode(result, "Description");
                                    if ("0".equals(status)) {
                                        progressDialog.dismiss();
                                        CommonUtils.showToast(getApplicationContext(), "添加成功");
                                        finish();
                                    } else if ("-1".equals(status)) {
                                        CommonUtils.write("采购入库:", json);
                                        progressDialog.dismiss();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(BuyActivity.this).setTitle("添加失败:").setMessage(Description).setNegativeButton("取消", null).show();
                                            }
                                        });
                                    }
                                }
                            };
                            HttpUtils.getResult(urlAdd, json, callBack);

                        }
                    }).show();
                    break;
                case R.id.tv_time://点击入库日期的监听
                    final Calendar c = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(BuyActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    c.set(year, monthOfYear, dayOfMonth);
                                    tv_time.setText(DateFormat.format("yyy.MM.dd", c));
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    break;
            }
        }
    };

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_buy;
    }

    @Override
    public void initView(View v) {
        buy_tv1 = ((TextView) findViewById(R.id.buy_tv1));
        buy_tv2 = ((TextView) findViewById(R.id.buy_tv2));
        tv_time = ((TextView) findViewById(R.id.tv_time));
        buy_lv = ((ListView) findViewById(R.id.buy_lv));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        item_foot = layoutInflater.inflate(R.layout.item_foot_buy, null);
        buy_lv.addFooterView(item_foot);
        buy_lv.setFooterDividersEnabled(false);
        buy_tv3 = ((TextView) item_foot.findViewById(R.id.buy_tv3));
        buy_tv4 = ((TextView) item_foot.findViewById(R.id.buy_tv4));
        buy_tv5 = ((TextView) item_foot.findViewById(R.id.buy_tv5));
        buy_bz = ((EditText) item_foot.findViewById(R.id.buy_bz));
        bt_add = ((Button) item_foot.findViewById(R.id.bt_add));

        objectAuth_22 = SPUtils.getString(getApplicationContext(), "ObjectAuth_22");
        if (objectAuth_22.equals("R")) {
            bt_add.setVisibility(View.GONE);
            tv_time.setBackgroundResource(R.drawable.bg_nochecked);
            tv_time.setEnabled(false);
            int i = Color.parseColor("#EBEFF1");
            buy_bz.setBackgroundColor(i);
            buy_bz.setEnabled(false);
        }

        bt_add.setOnClickListener(onClick);
        tv_time.setOnClickListener(onClick);
        myAdapter = new MyAdapter();
        buy_lv.setAdapter(myAdapter);
        initTime();

        //reJson={"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410","DocEntry":62}
        SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sp.getString("username", null);
        String password = sp.getString("password", null);
        String dbName = SPUtils.getString(getApplicationContext(), "DBName");
        Intent intent = getIntent();
        String docEntry = intent.getStringExtra("docEntry");
        String url = intent.getStringExtra("url");
        String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbName + "\",\"DocEntry\":" + docEntry + "}";
        Callback callBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                getOpenPODetail = new Gson().fromJson(result, GetOpenPODetail.class);
                Message message = Message.obtain();
                message.obj = getOpenPODetail;
                handler.sendMessage(message);

            }
        };
        HttpUtils.getResult(url, json, callBack);

    }

    //外部listview的适配器
    private class MyAdapter extends BaseAdapter {

        private List<GetOpenPODetail.Linesbean.WhsItemsbean> whsItems;
        private String trim;

        @Override
        public int getCount() {
            return lines == null ? 0 : lines.size();
        }

        @Override
        public Object getItem(int position) {
            return lines.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            double sum1 = 0.0;
            double sum2 = 0.0;
            double sum3 = 0.0;
            for (int i = 0; i <= position; i++) {
                sum1 += lines.get(i).getBagAmount();
                sum2 += lines.get(i).getAmount();
                sum3 += lines.get(i).getLineGross();
            }
            buy_tv3.setText(String.valueOf(sum1));
            buy_tv4.setText(CommonUtils.addComma(String.valueOf(sum2)));
            buy_tv5.setText(String.valueOf(sum3));

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(BuyActivity.this, R.layout.ac_item1_1, null);
                holder.ac_item_iv = (ImageView) convertView.findViewById(R.id.ac_item_iv);
                holder.ac_item1_1 = ((TextView) convertView.findViewById(R.id.ac_item1_1));
                holder.ac_item1_2 = ((TextView) convertView.findViewById(R.id.ac_item1_2));
                holder.ac_item1_3 = ((EditText) convertView.findViewById(R.id.ac_item1_3));
                holder.ac_item1_4 = ((EditText) convertView.findViewById(R.id.ac_item1_4));
                holder.ac_item1_5 = ((TextView) convertView.findViewById(R.id.ac_item1_5));
                holder.ac_item1_6 = ((EditText) convertView.findViewById(R.id.ac_item1_6));
                holder.ac_item1_7 = ((TextView) convertView.findViewById(R.id.ac_item1_7));
                holder.ac_item1_8 = ((TextView) convertView.findViewById(R.id.ac_item1_8));
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }

            holder.ac_item_iv.setTag(position);
            holder.ac_item1_1.setTag(position);
            holder.ac_item1_2.setTag(position);
            holder.ac_item1_3.setTag(position);
            holder.ac_item1_4.setTag(position);
            holder.ac_item1_5.setTag(position);
            holder.ac_item1_6.setTag(position);
            holder.ac_item1_7.setTag(position);
            holder.ac_item1_8.setTag(position);

            holder.ac_item1_1.setText(lines.get(position).getItemCode());
            holder.ac_item1_2.setText(lines.get(position).getDscription());
            holder.ac_item1_3.setText(String.valueOf(lines.get(position).getBagAmount()));
            holder.ac_item1_4.setText(CommonUtils.addComma(String.valueOf(lines.get(position).getAmount())));
            holder.ac_item1_5.setText(lines.get(position).getWhsCode());
            holder.ac_item1_6.setText(lines.get(position).getBatchNum());
            holder.ac_item1_7.setText(String.valueOf(lines.get(position).getPriceAfVat()));
            holder.ac_item1_8.setText(String.valueOf(lines.get(position).getLineGross()));

            String objectPriceAuth_22 = SPUtils.getString(getApplicationContext(), "ObjectPriceAuth_22");
            final String objectAmountAuth_22 = SPUtils.getString(getApplicationContext(), "ObjectAmountAuth_22");
            if (objectAuth_22.equals("R")) {
                holder.ac_item_iv.setVisibility(View.INVISIBLE);
                int i = Color.parseColor("#EBEFF1");
                holder.ac_item1_3.setBackgroundColor(i);
                holder.ac_item1_4.setBackgroundColor(i);
                holder.ac_item1_5.setBackgroundColor(i);
                holder.ac_item1_6.setBackgroundColor(i);
                holder.ac_item1_3.setEnabled(false);
                holder.ac_item1_4.setEnabled(false);
                holder.ac_item1_5.setEnabled(false);
                holder.ac_item1_6.setEnabled(false);
            }
            if (objectPriceAuth_22.equals("N")) {
                holder.ac_item1_7.setText("***");
            }
            if (objectAmountAuth_22.equals("N")) {
                holder.ac_item1_8.setText("***");
                buy_tv5.setText("***");
            }

            holder.ac_item1_3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    trim = holder.ac_item1_3.getText().toString().trim();
                    int tag = (int) holder.ac_item1_3.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    lines.get(tag).setBagAmount(Double.parseDouble(trim));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item1_4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    trim = holder.ac_item1_4.getText().toString().trim().replace(",", "");
                    int tag = (int) holder.ac_item1_4.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    double v1 = Double.parseDouble(trim);
                    double v2 = lines.get(tag).getPriceAfVat();
                    double v3 = CommonUtils.mul(v1, v2);
                    lines.get(tag).setAmount(v1);
                    lines.get(tag).setLineGross(v3);
                    holder.ac_item1_8.setText(String.valueOf(v3));
                    if (objectAmountAuth_22.equals("N")) {
                        holder.ac_item1_8.setText("***");
                        buy_tv5.setText("***");
                    }
                    if (!CommonUtils.touzi_ed_values22.equals(holder.ac_item1_4.getText().toString().trim().replace(",", ""))) {
                        holder.ac_item1_4.setText(CommonUtils.addCommas(holder.ac_item1_4.getText().toString().trim().replace(",", ""), holder.ac_item1_4));
                        holder.ac_item1_4.setSelection(CommonUtils.addCommas(holder.ac_item1_4.getText().toString().trim().replace(",", ""), holder.ac_item1_4).length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item1_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ListView lv = new ListView(BuyActivity.this);
                    final int position = (int) holder.ac_item1_5.getTag();
                    whsItems = lines.get(position).getWhsItems();
                    //lv.setVerticalScrollBarEnabled(false); 设置滑动标取消
                    lv.setAdapter(new CKAdapter());
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                            holder.ac_item1_5.setText(whsItems.get(i).getWhsCode());
                            lines.get(position).setWhsCode(holder.ac_item1_5.getText().toString().trim());
                        }

                    });
                    PopupWindow popupWindow = null;
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv, 500, 500);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(holder.ac_item1_5, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }

                }
            });

            holder.ac_item1_6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    trim = holder.ac_item1_6.getText().toString().trim();
                    int position = (int) holder.ac_item1_6.getTag();
                    lines.get(position).setBatchNum(trim);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lines.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {

            ImageView ac_item_iv;
            TextView ac_item1_1;
            TextView ac_item1_2;
            EditText ac_item1_3;
            EditText ac_item1_4;
            TextView ac_item1_5;
            EditText ac_item1_6;
            TextView ac_item1_7;
            TextView ac_item1_8;
        }

        //内部listview的适配器
        private class CKAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return whsItems == null ? 0 : whsItems.size();
            }

            @Override
            public Object getItem(int position) {
                return whsItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = View.inflate(BuyActivity.this, R.layout.ac_item1_2, null);
                    viewHolder.ac_item2_1 = (TextView) convertView.findViewById(R.id.ac_item2_1);
                    viewHolder.ac_item2_2 = (TextView) convertView.findViewById(R.id.ac_item2_2);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = ((ViewHolder) convertView.getTag());
                }
                viewHolder.ac_item2_1.setTag(position);
                viewHolder.ac_item2_2.setTag(position);

                viewHolder.ac_item2_1.setText(whsItems.get(position).getWhsName());
                viewHolder.ac_item2_2.setText(whsItems.get(position).getWhsCode());
                return convertView;
            }

            class ViewHolder {

                TextView ac_item2_1;
                TextView ac_item2_2;
            }
        }

    }

}
