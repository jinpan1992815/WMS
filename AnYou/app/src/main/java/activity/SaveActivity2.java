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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.GetInvTransDetail;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2017/1/4 0004.
 */
public class SaveActivity2 extends BaseActivity {

    private TextView save2_tv1;
    private TextView save2_tv2;
    private TextView save2_tv3;
    private TextView save2_tv4;
    private TextView save2_tv5;
    private TextView save2_tv6;
    private EditText save2_bz;
    private Button save2_add;
    private ListView save2_lv;
    private MyAdapter myAdapter;
    private ProgressDialog progressDialog;
    private GetInvTransDetail getInvTransDetail;
    private List<GetInvTransDetail.InvLinesbean> lines;
    private GetInvTransDetail.InvLinesbean linesbean;
    private List<GetInvTransDetail.ItemListbean> itemList;
    private List<GetInvTransDetail.InvLinesbean.Batchesbean> batches;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            lines = getInvTransDetail.getInvLines();
            itemList = getInvTransDetail.getItemList();
            myAdapter.notifyDataSetChanged();

            save2_tv1.setText(getInvTransDetail.getTransName());
            save2_tv2.setText(getInvTransDetail.getFiller());
            save2_tv4.setText(getInvTransDetail.getToWhs());
            ;
        }
    };
    private String objectAuth_67;

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_save2;
    }

    @Override
    public void initView(View v) {
        save2_tv1 = ((TextView) findViewById(R.id.save2_tv1));
        save2_tv2 = ((TextView) findViewById(R.id.save2_tv2));
        save2_tv3 = ((TextView) findViewById(R.id.save2_tv3));
        save2_tv4 = ((TextView) findViewById(R.id.save2_tv4));
        save2_lv = ((ListView) findViewById(R.id.save2_lv));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View item_foot_save2 = layoutInflater.inflate(R.layout.item_foot_save2, null);
        save2_lv.addFooterView(item_foot_save2);
        save2_lv.setFooterDividersEnabled(false);
        save2_tv5 = ((TextView) item_foot_save2.findViewById(R.id.save2_tv5));
        save2_tv6 = ((TextView) item_foot_save2.findViewById(R.id.save2_tv6));
        save2_bz = ((EditText) item_foot_save2.findViewById(R.id.save2_bz));
        save2_add = ((Button) item_foot_save2.findViewById(R.id.save2_add));

        objectAuth_67 = SPUtils.getString(getApplicationContext(), "ObjectAuth_67");
        if (objectAuth_67.equals("R")) {
            save2_add.setVisibility(View.GONE);
            save2_tv3.setBackgroundResource(R.drawable.bg_nochecked);
            save2_tv3.setEnabled(false);
            int i = Color.parseColor("#EBEFF1");
            save2_bz.setBackgroundColor(i);
            save2_bz.setEnabled(false);
        }

        save2_add.setOnClickListener(onClick);
        save2_tv3.setOnClickListener(onClick);
        myAdapter = new MyAdapter();
        save2_lv.setAdapter(myAdapter);
        initTime();
        initData();
    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save2_add: //点击添加按钮的监听
                    new AlertDialog.Builder(SaveActivity2.this).setTitle("提示")//设置对话框标题
                            .setMessage("是否添加未清的库存转储?")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //等待动画
                            progressDialog = ProgressDialog.show(SaveActivity2.this, "", "加载中，请稍后……");

                            //连接服务器获取数据
                            SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            final String username = sp.getString("username", null);
                            final String password = sp.getString("password", null);
                            final String dbname = SPUtils.getString(getApplicationContext(), "DBName");
                            String DocDate = save2_tv3.getText().toString();
                            String TrsCode = getInvTransDetail.getTransCode();
                            String Comments = save2_bz.getText().toString();
                            String filler = getInvTransDetail.getFiller();
                            String toWhs = getInvTransDetail.getToWhs();

                            String s = new Gson().toJson(lines);
                            final String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbname + "\"," +
                                    "\"DocDate\":\"" + DocDate + "\",\"TrsCode\":\"" + TrsCode + "\",\"Comments\":\"" + Comments + "\"," +
                                    "\"Filler\":\"" + filler + "\",\"ToWhs\":\"" + toWhs + "\",\"Lines\":" + s + "}";
                            Callback callBack = new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    progressDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(SaveActivity2.this).setTitle("添加失败:").setMessage("没有连接到服务器").setNegativeButton("取消", null).show();
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
                                        CommonUtils.write("库存转储复制到:", json);
                                        progressDialog.dismiss();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(SaveActivity2.this).setTitle("添加失败:").setMessage(Description).setNegativeButton("取消", null).show();
                                            }
                                        });
                                    }
                                }
                            };
                            HttpUtils.getResult(Constant.HOST_AddInventoryTransfer, json, callBack);

                        }
                    }).show();
                    break;
                case R.id.save2_tv3://点击过账日期的监听
                    final Calendar c = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(SaveActivity2.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    c.set(year, monthOfYear, dayOfMonth);
                                    save2_tv3.setText(DateFormat.format("yyy.MM.dd", c));
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    break;
            }

        }
    };


    //默认显示当天时间
    private void initTime() {
        String format = CommonUtils.getCurrentTime();
        save2_tv3.setText(format);
    }

    //请求数据
    private void initData() {
        //reJson={"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410","DocEntry":1}
        SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sp.getString("username", null);
        String password = sp.getString("password", null);
        String dbName = SPUtils.getString(getApplicationContext(), "DBName");
        Intent intent = getIntent();
        String docEntry = intent.getStringExtra("docEntry");
        String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbName + "\",\"DocEntry\":" + docEntry + "}";
        Callback callBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                getInvTransDetail = new Gson().fromJson(result, GetInvTransDetail.class);
                Message message = Message.obtain();
                message.obj = getInvTransDetail;
                handler.sendMessage(message);

            }
        };
        HttpUtils.getResult(Constant.HOST_GetInvTransDetail, json, callBack);

    }

    //外部listview的适配器
    private class MyAdapter extends BaseAdapter {
        private List<GetInvTransDetail.ItemListbean.QtyInWhsbean> qtyInWhs;

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
            linesbean = lines.get(position);
            double sum1 = 0.0;
            double sum2 = 0.0;
            for (int i = 0; i <= position; i++) {
                sum1 += lines.get(i).getBagAmount();
                sum2 += lines.get(i).getAmount();
            }
            save2_tv5.setText(String.valueOf(sum1));
            save2_tv6.setText(CommonUtils.addComma(String.valueOf(sum2)));

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(SaveActivity2.this, R.layout.ac_item4_2, null);
                holder.ac_item_iv = (ImageView) convertView.findViewById(R.id.ac_item4_iv);
                holder.ac_item4_1 = ((TextView) convertView.findViewById(R.id.ac_item4_1));
                holder.ac_item4_2 = ((TextView) convertView.findViewById(R.id.ac_item4_2));
                holder.ac_item4_3 = ((EditText) convertView.findViewById(R.id.ac_item4_3));
                holder.ac_item4_4 = ((EditText) convertView.findViewById(R.id.ac_item4_4));
                holder.ac_item4_5 = ((TextView) convertView.findViewById(R.id.ac_item4_5));
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }

            holder.ac_item_iv.setTag(position);
            holder.ac_item4_1.setTag(position);
            holder.ac_item4_2.setTag(position);
            holder.ac_item4_3.setTag(position);
            holder.ac_item4_4.setTag(position);
            holder.ac_item4_5.setTag(position);

            holder.ac_item4_1.setText(linesbean.getItemCode());
            holder.ac_item4_2.setText(linesbean.getDscription());
            holder.ac_item4_3.setText(String.valueOf(linesbean.getBagAmount()));
            holder.ac_item4_3.setSelection(holder.ac_item4_3.getText().length());
            holder.ac_item4_4.setText(CommonUtils.addComma(String.valueOf(linesbean.getAmount())));
            holder.ac_item4_4.setSelection(holder.ac_item4_4.getText().length());
            holder.ac_item4_5.setText("可选 ->");

            if (objectAuth_67.equals("R")) {
                holder.ac_item_iv.setVisibility(View.INVISIBLE);
                int i = Color.parseColor("#EBEFF1");
                holder.ac_item4_3.setBackgroundColor(i);
                holder.ac_item4_4.setBackgroundColor(i);
                holder.ac_item4_5.setBackgroundColor(i);
                holder.ac_item4_3.setEnabled(false);
                holder.ac_item4_4.setEnabled(false);
                holder.ac_item4_5.setEnabled(false);
            }

            holder.ac_item_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lines.remove(position);
                    notifyDataSetChanged();
                }
            });

            holder.ac_item4_3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String trim = holder.ac_item4_3.getText().toString().trim();
                    int position = (int) holder.ac_item4_3.getTag();
                    if (trim == null || trim.length() == 0) {
                        lines.get(position).setBagAmount(0.0);
                        return;
                    }
                    lines.get(position).setBagAmount(Double.parseDouble(trim));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item4_4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String trim = holder.ac_item4_4.getText().toString().trim().replace(",", "");
                    int position = (int) holder.ac_item4_4.getTag();
                    if (trim == null || trim.length() == 0) {
                        lines.get(position).setAmount(0.0);
                        return;
                    }
                    lines.get(position).setAmount(Double.parseDouble(trim));
                    if (!CommonUtils.touzi_ed_values22.equals(holder.ac_item4_4.getText().toString().trim().replace(",", ""))) {
                        holder.ac_item4_4.setText(CommonUtils.addCommas(holder.ac_item4_4.getText().toString().trim().replace(",", ""), holder.ac_item4_4));
                        holder.ac_item4_4.setSelection(CommonUtils.addCommas(holder.ac_item4_4.getText().toString().trim().replace(",", ""), holder.ac_item4_4).length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item4_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String batchNumber = null;

                    ListView lv2 = new ListView(SaveActivity2.this);
                    final int tag = (int) holder.ac_item4_5.getTag();
                    qtyInWhs = itemList.get(position).getQtyInWhs();
                    ArrayList<GetInvTransDetail.ItemListbean.QtyInWhsbean> list = new ArrayList<>();
                    batches = lines.get(tag).getBatches();
                    for (GetInvTransDetail.InvLinesbean.Batchesbean batch : batches) {
                        batchNumber = batch.getBatchNumber();
                    }
                    for (GetInvTransDetail.ItemListbean.QtyInWhsbean qtyInWh : qtyInWhs) {
                        if (batchNumber.equals(qtyInWh.getBatchNumber())) {
                            list.add(qtyInWh);
                        }
                    }
                    lv2.setAdapter(new PCAdapter(tag, list));

                    PopupWindow popupWindow = null;
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv2, 550, 300);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(holder.ac_item4_5, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }

                }
            });


            return convertView;
        }

        class ViewHolder {

            ImageView ac_item_iv;
            TextView ac_item4_1;
            TextView ac_item4_2;
            EditText ac_item4_3;
            EditText ac_item4_4;
            TextView ac_item4_5;
        }

        //内部批次listview的适配器
        private class PCAdapter extends BaseAdapter {
            private final int tag;
            private final ArrayList<GetInvTransDetail.ItemListbean.QtyInWhsbean> list;

            public PCAdapter(int tag, ArrayList<GetInvTransDetail.ItemListbean.QtyInWhsbean> list) {
                this.tag = tag;
                this.list = list;
            }

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
            public View getView(final int position, View convertView, ViewGroup parent) {
                final ViewHolder viewHolder;
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = View.inflate(SaveActivity2.this, R.layout.ac_item2_3, null);
                    viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                    viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                    viewHolder.ac_item2_5 = (EditText) convertView.findViewById(R.id.ac_item2_5);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = ((ViewHolder) convertView.getTag());
                }
                viewHolder.ac_item2_3.setTag(position);
                viewHolder.ac_item2_4.setTag(position);
                viewHolder.ac_item2_5.setTag(position);
                qtyInWhs = itemList.get(tag).getQtyInWhs();
                batches = lines.get(tag).getBatches();

                viewHolder.ac_item2_3.setText(qtyInWhs.get(position).getBatchNumber());
                viewHolder.ac_item2_4.setText(String.valueOf(qtyInWhs.get(position).getQuanity()));
                String text = viewHolder.ac_item2_3.getText().toString().trim();
                if (batches.get(position).getBatchNumber().equals(text)) {
                    viewHolder.ac_item2_5.setText(String.valueOf(batches.get(position).getQuantity()));
                }
                viewHolder.ac_item2_5.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String trim = viewHolder.ac_item2_5.getText().toString().trim();
                        int pcTag = (int) viewHolder.ac_item2_5.getTag();
                        if (trim == null || trim.length() == 0) {
                            trim = "0";
                        }
                        lines.get(tag).getBatches().get(pcTag).setQuantity(Double.parseDouble(trim));
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                return convertView;
            }

            class ViewHolder {

                TextView ac_item2_3;
                TextView ac_item2_4;
                EditText ac_item2_5;
            }

        }

    }
}
