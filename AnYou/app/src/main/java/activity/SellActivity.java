package activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import bean.GetOpenSODetail;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/7 0007.
 */
public class SellActivity extends BaseActivity {
    private TextView sell_tv1;
    private TextView sell_tv2;
    private TextView tv_time2;
    private TextView sell_tv3;
    private TextView sell_tv4;
    private TextView sell_tv5;
    private ListView sell_lv;
    private EditText sell_bz;
    private Button bt_add2;
    private ProgressDialog progressDialog;
    private GetOpenSODetail getOpenSODetail;
    private List<GetOpenSODetail.Linesbean> lines;
    private MyAdapter myAdapter;
    private Button handwrite;
    private ImageView img;
    private String objectAuth_15;
    private View item_foot_sell;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            lines = getOpenSODetail.getLines();
            myAdapter.notifyDataSetChanged();

            sell_tv1.setText(getOpenSODetail.getCardCode());
            sell_tv2.setText(getOpenSODetail.getCardName());
        }
    };

    //默认显示当天时间
    private void initTime() {
        String format = CommonUtils.getCurrentTime();
        tv_time2.setText(format);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_add2: //点击添加按钮的监听
//                    if (img.getDrawable() == null) {
//                        CommonUtils.showToast(SellActivity.this, "请签名");
//                        return;
//                    }

                    new AlertDialog.Builder(SellActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("是否添加销售出库单?")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        private String attachment;

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //等待动画
                            progressDialog = ProgressDialog.show(SellActivity.this, "", "正在添加出库单，请稍后……");

                            SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            final String username = sp.getString("username", null);
                            final String password = sp.getString("password", null);
                            final String dbname = SPUtils.getString(getApplicationContext(), "DBName");
                            String s = new Gson().toJson(getOpenSODetail.getLines());
                            String DocDate = tv_time2.getText().toString();
                            String CardCode = sell_tv1.getText().toString();
                            String CardName = sell_tv2.getText().toString();
                            String Comments = sell_bz.getText().toString().trim();
                            Intent intent = getIntent();
                            String docEntry = intent.getStringExtra("docEntry");

                            Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/Anyou/qm.png");
                            if (bitmap != null) {

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                bitmap.recycle();
                                byte[] bytes = baos.toByteArray();
                                try {
                                    baos.close();
                                } catch (IOException e) {
                                }
                                String str = Base64.encodeToString(bytes, Base64.DEFAULT);
                                attachment = str.replace("+", "%2B");
                            }
                            if (img.getDrawable() == null) {
                                attachment = "";
                            }
                            // "reJson={""UserName"":""testAccount"",""Password"":""1234"",""DBName"":""ANSA410"",""CardCode"":""C000046"",""CardName"":""闫云维"",""DocDate"":""2016.12.19"",""DocEntry"":233,""Comments"":""shiyixia"",""Attachment"":null,""Lines"":s}"
                            String json = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"CardCode\":\"" + CardCode + "\"," +
                                    "\"CardName\":\"" + CardName + "\",\"DocDate\":\"" + DocDate + "\",\"DocEntry\":" + docEntry + "," + "\"Comments\":\"" + Comments + "\"" + "," +
                                    "\"Attachment\":\"" + attachment + "\",\"Lines\":" + s + "}";
                            final String jsons = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"CardCode\":\"" + CardCode + "\"," +
                                    "\"CardName\":\"" + CardName + "\",\"DocDate\":\"" + DocDate + "\",\"DocEntry\":" + docEntry + "," + "\"Comments\":\"" + Comments + "\"" + "," +
                                    "\",\"Lines\":" + s + "}";
                            Callback callBack = new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    progressDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(SellActivity.this).setTitle("添加失败:").setMessage("没有连接到服务器").setNegativeButton("取消", null).show();
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
                                        CommonUtils.write("销售出库:", jsons);
                                        progressDialog.dismiss();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(SellActivity.this).setTitle("添加失败:").setMessage(Description).setNegativeButton("取消", null).show();
                                            }
                                        });
                                    }
                                }
                            };
                            HttpUtils.getResult(Constant.HOST_AddODLNBasedonSO, json, callBack);

                        }
                    }).show();
                    break;
                case R.id.tv_time2://点击入库日期的监听
                    final Calendar c = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(SellActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    c.set(year, monthOfYear, dayOfMonth);
                                    tv_time2.setText(DateFormat.format("yyy.MM.dd", c));
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    break;
                case R.id.bt_handwrite://签名按钮点击事件的监听
                    Intent intent = new Intent();
                    intent.setClass(SellActivity.this, HandWriteActivity.class);
                    startActivityForResult(intent, 100);
                    break;
            }
        }
    };

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_sell;
    }

    @Override
    public void initView(View v) {
        sell_tv1 = ((TextView) findViewById(R.id.sell_tv1));
        sell_tv2 = ((TextView) findViewById(R.id.sell_tv2));
        tv_time2 = ((TextView) findViewById(R.id.tv_time2));
        sell_lv = ((ListView) findViewById(R.id.sell_lv));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        item_foot_sell = layoutInflater.inflate(R.layout.item_foot_sell, null);
        sell_lv.addFooterView(item_foot_sell);
        sell_lv.setFooterDividersEnabled(false);
        sell_tv3 = ((TextView) item_foot_sell.findViewById(R.id.sell_tv3));
        sell_tv4 = ((TextView) item_foot_sell.findViewById(R.id.sell_tv4));
        sell_tv5 = ((TextView) item_foot_sell.findViewById(R.id.sell_tv5));
        sell_bz = ((EditText) item_foot_sell.findViewById(R.id.sell_bz));
        bt_add2 = ((Button) findViewById(R.id.bt_add2));
        handwrite = ((Button) findViewById(R.id.bt_handwrite));
        img = ((ImageView) findViewById(R.id.img));

        objectAuth_15 = SPUtils.getString(getApplicationContext(), "ObjectAuth_15");
        if (objectAuth_15.equals("R")) {
            handwrite.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
            bt_add2.setVisibility(View.GONE);
            tv_time2.setBackgroundResource(R.drawable.bg_nochecked);
            tv_time2.setEnabled(false);
            int i = Color.parseColor("#EBEFF1");
            sell_bz.setBackgroundColor(i);
            sell_bz.setEnabled(false);
        }

        bt_add2.setOnClickListener(onClick);
        tv_time2.setOnClickListener(onClick);
        handwrite.setOnClickListener(onClick);
        myAdapter = new MyAdapter();
        sell_lv.setAdapter(myAdapter);
        initTime();

        //reJson={"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410","DocEntry":62}
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
                getOpenSODetail = new Gson().fromJson(result, GetOpenSODetail.class);
                Message message = Message.obtain();
                message.obj = getOpenSODetail;
                handler.sendMessage(message);

            }
        };
        HttpUtils.getResult(Constant.HOST_GetOpenSODetail, json, callBack);

    }

    //外部listview的适配器
    private class MyAdapter extends BaseAdapter {

        private List<GetOpenSODetail.Linesbean.WhsItemsbean> whsItems;
        private List<GetOpenSODetail.Linesbean.WhsItemsbean.QtyInWhsbean> qtyInWhs;
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
            sell_tv3.setText(String.valueOf(sum1));
            sell_tv4.setText(CommonUtils.addComma(String.valueOf(sum2)));
            sell_tv5.setText(String.valueOf(sum3));

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(SellActivity.this, R.layout.ac_item2_1, null);
                holder.ac_item_iv2 = (ImageView) convertView.findViewById(R.id.ac_item_iv2);
                holder.ac_item2_1 = ((TextView) convertView.findViewById(R.id.ac_item2_1));
                holder.ac_item2_2 = ((TextView) convertView.findViewById(R.id.ac_item2_2));
                holder.ac_item2_3 = ((EditText) convertView.findViewById(R.id.ac_item2_3));
                holder.ac_item2_4 = ((TextView) convertView.findViewById(R.id.ac_item2_4));
                holder.ac_item2_5 = ((TextView) convertView.findViewById(R.id.ac_item2_5));
                holder.ac_item2_6 = ((TextView) convertView.findViewById(R.id.ac_item2_6));
                holder.ac_item2_7 = ((TextView) convertView.findViewById(R.id.ac_item2_7));
                holder.ac_item2_8 = ((TextView) convertView.findViewById(R.id.ac_item2_8));
                holder.cb = ((CheckBox) convertView.findViewById(R.id.cb));
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }

            holder.ac_item_iv2.setTag(position);
            holder.ac_item2_1.setTag(position);
            holder.ac_item2_2.setTag(position);
            holder.ac_item2_3.setTag(position);
            holder.ac_item2_4.setTag(position);
            holder.ac_item2_5.setTag(position);
            holder.ac_item2_6.setTag(position);
            holder.ac_item2_7.setTag(position);
            holder.ac_item2_8.setTag(position);
            holder.cb.setTag(position);

            holder.ac_item2_1.setText(lines.get(position).getItemCode());
            holder.ac_item2_2.setText(lines.get(position).getDscription());
            holder.ac_item2_3.setText(String.valueOf(lines.get(position).getBagAmount()));
            holder.ac_item2_4.setText(CommonUtils.addComma(String.valueOf(lines.get(position).getAmount())));
            holder.ac_item2_5.setText(lines.get(position).getWhsCode());
            holder.ac_item2_6.setText("可选 ->");
            holder.ac_item2_7.setText(String.valueOf(lines.get(position).getPriceAfVat()));
            holder.ac_item2_8.setText(String.valueOf(lines.get(position).getLineGross()));

            String ObjectPriceAuth_15 = SPUtils.getString(getApplicationContext(), "ObjectPriceAuth_15");
            final String ObjectAmountAuth_15 = SPUtils.getString(getApplicationContext(), "ObjectAmountAuth_15");
            if (objectAuth_15.equals("R")) {
                holder.ac_item_iv2.setVisibility(View.INVISIBLE);
                int i = Color.parseColor("#EBEFF1");
                holder.ac_item2_3.setBackgroundColor(i);
                holder.ac_item2_5.setBackgroundColor(i);
                holder.ac_item2_6.setBackgroundColor(i);
                holder.ac_item2_3.setEnabled(false);
                holder.ac_item2_5.setEnabled(false);
                holder.ac_item2_6.setEnabled(false);
            }
            if (ObjectPriceAuth_15.equals("N")) {
                holder.ac_item2_7.setText("***");
            }
            if (ObjectAmountAuth_15.equals("N")) {
                holder.ac_item2_8.setText("***");
                sell_tv5.setText("***");
            }

            holder.ac_item2_3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    trim = holder.ac_item2_3.getText().toString().trim();
                    int tag = (int) holder.ac_item2_3.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    double factor1 = lines.get(tag).getFactor1();
                    double v = Double.parseDouble(trim);
                    double i = CommonUtils.mul(factor1, v);
                    holder.ac_item2_4.setText(CommonUtils.addComma(String.valueOf(i)));

                    double priceAfVat = lines.get(tag).getPriceAfVat();
                    double v1 = CommonUtils.mul(priceAfVat, i);
                    holder.ac_item2_8.setText(String.valueOf(v1));
                    lines.get(tag).setBagAmount(v);
                    lines.get(tag).setAmount(i);
                    lines.get(tag).setLineGross(v1);
                    if (ObjectAmountAuth_15.equals("N")) {
                        holder.ac_item2_8.setText("***");
                        sell_tv5.setText("***");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item2_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ListView lv1 = new ListView(SellActivity.this);
                    final int tag = (int) holder.ac_item2_5.getTag();
                    //lv.setVerticalScrollBarEnabled(false); 设置滑动标取消
                    whsItems = lines.get(tag).getWhsItems();
                    lv1.setAdapter(new CKAdapter());
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                            holder.ac_item2_5.setText(whsItems.get(i).getWhsCode());
                            lines.get(tag).setWhsCode(holder.ac_item2_5.getText().toString().trim());
                        }

                    });
                    PopupWindow popupWindow = null;
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv1, 500, 500);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(holder.ac_item2_5, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }

                }
            });

            holder.ac_item2_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListView lv2 = new ListView(SellActivity.this);
                    final int tag = (int) holder.ac_item2_6.getTag();
                    whsItems = lines.get(tag).getWhsItems();
                    for (GetOpenSODetail.Linesbean.WhsItemsbean whsItem : whsItems) {
                        if (whsItem.getWhsCode().equals(holder.ac_item2_5.getText().toString())) {
                            lv2.setAdapter(new PCAdapter(whsItem));
                            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    holder.ac_item2_6.setText(qtyInWhs.get(tag).getBatchNumber());
                                }
                            });
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
                                popupWindow.showAsDropDown(holder.ac_item2_6, 0, 0);
                                popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        return false;
                                    }
                                });
                            }
                        }
                    }
                }
            });

            holder.ac_item_iv2.setVisibility(View.INVISIBLE);

            return convertView;
        }

        class ViewHolder {

            ImageView ac_item_iv2;
            TextView ac_item2_1;
            TextView ac_item2_2;
            EditText ac_item2_3;
            TextView ac_item2_4;
            TextView ac_item2_5;
            TextView ac_item2_6;
            TextView ac_item2_7;
            TextView ac_item2_8;
            CheckBox cb;
        }

        //内部仓库listview的适配器
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
                    convertView = View.inflate(SellActivity.this, R.layout.ac_item2_2, null);
                    viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                    viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = ((ViewHolder) convertView.getTag());
                }
                viewHolder.ac_item2_3.setTag(position);
                viewHolder.ac_item2_4.setTag(position);

                viewHolder.ac_item2_3.setText(whsItems.get(position).getWhsName());
                viewHolder.ac_item2_4.setText(whsItems.get(position).getWhsCode());
                return convertView;
            }

            class ViewHolder {

                TextView ac_item2_3;
                TextView ac_item2_4;
            }
        }

        //内部批次listview的适配器
        private class PCAdapter extends BaseAdapter {

            private final GetOpenSODetail.Linesbean.WhsItemsbean whsItem;

            public PCAdapter(GetOpenSODetail.Linesbean.WhsItemsbean whsItem) {
                this.whsItem = whsItem;
                qtyInWhs = whsItem.getQtyInWhs();
            }

            @Override
            public int getCount() {
                return qtyInWhs == null ? 0 : qtyInWhs.size();
            }

            @Override
            public Object getItem(int position) {
                return qtyInWhs.get(position);
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
                    convertView = View.inflate(SellActivity.this, R.layout.ac_item2_3, null);
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

                viewHolder.ac_item2_3.setText(qtyInWhs.get(position).getBatchNumber());
                viewHolder.ac_item2_4.setText(String.valueOf(qtyInWhs.get(position).getQuanity()));
                viewHolder.ac_item2_5.setText(String.valueOf(qtyInWhs.get(position).getAmount()));

                viewHolder.ac_item2_5.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        trim = viewHolder.ac_item2_5.getText().toString().trim();
                        int pcTag = (int) viewHolder.ac_item2_5.getTag();
                        if (trim == null || trim.length() == 0) {
                            trim = "0.0";
                        }
                        whsItem.getQtyInWhs().get(pcTag).setAmount(Double.parseDouble(trim));

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

    //手写签名的缩略图
    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Anyou/qm.png";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 100) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bm = BitmapFactory.decodeFile(path, options);
            img.setImageBitmap(bm);
        }
    }

}

