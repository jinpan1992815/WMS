package activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.KeyEvent;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.GetInvInformation;
import bean.SaveLine;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2017/1/10 0010.
 */

//库存转储直接制单界面
public class SaveActivity1 extends BaseActivity {
    private TextView save1_tv1;
    private TextView save1_tv2;
    private TextView save1_tv3;
    private TextView save1_tv4;
    private ImageView ac_iv_add;
    private TextView save1_tv5;
    private TextView save1_tv6;
    private EditText save1_bz;
    private Button save1_add;
    private ListView save1_lv;
    private GetInvInformation getInvInformation;
    private List<GetInvInformation.TransCodeListbean> transCodeList;
    private List<GetInvInformation.WhsListbean> whsList;
    private MyAdapter myAdapter;
    private List<GetInvInformation.ItemListbean> itemList;
    private ProgressDialog progressDialog;
    private String transCode;
    private ArrayList<SaveLine> lines;
    private List<GetInvInformation.ItemListbean.QtyInWhsbean> qtyInWhs;
    private List<List<GetInvInformation.ItemListbean.QtyInWhsbean>> lists = new ArrayList<>();
    private List<SaveLine.Batchesbean> batches;
    private BMAdapter bmAdapter;
    private List<GetInvInformation.ItemListbean> list;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                progressDialog.dismiss();
                CommonUtils.showToast(getApplicationContext(), "数据加载完毕");
            }
            itemList = getInvInformation.getItemList();
            myAdapter.notifyDataSetChanged();

        }
    };

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_save1;
    }

    @Override
    public void initView(View v) {
        progressDialog = ProgressDialog.show(SaveActivity1.this, "", "数据加载中，请稍后……");
        save1_tv1 = ((TextView) findViewById(R.id.save1_tv1));
        save1_tv2 = ((TextView) findViewById(R.id.save1_tv2));
        save1_tv3 = ((TextView) findViewById(R.id.save1_tv3));
        save1_tv4 = ((TextView) findViewById(R.id.save1_tv4));
        save1_lv = ((ListView) findViewById(R.id.save1_lv));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View item_foot_save = layoutInflater.inflate(R.layout.item_foot_save1, null);
        save1_lv.addFooterView(item_foot_save);
        save1_lv.setFooterDividersEnabled(false);
        ac_iv_add = ((ImageView) item_foot_save.findViewById(R.id.ac_iv_add));
        save1_tv5 = ((TextView) item_foot_save.findViewById(R.id.save1_tv5));
        save1_tv6 = ((TextView) item_foot_save.findViewById(R.id.save1_tv6));
        save1_bz = ((EditText) item_foot_save.findViewById(R.id.save1_bz));
        save1_add = ((Button) item_foot_save.findViewById(R.id.save1_add));

        initTime();
        initData();
        save1_tv1.setOnClickListener(onClick);
        save1_tv2.setOnClickListener(onClick);
        save1_tv3.setOnClickListener(onClick);
        save1_tv4.setOnClickListener(onClick);
        ac_iv_add.setOnClickListener(onClick);
        save1_add.setOnClickListener(onClick);

        lines = new ArrayList<>();
        myAdapter = new MyAdapter(lines);
        save1_lv.setAdapter(myAdapter);
    }

    //默认显示当天时间
    private void initTime() {
        String format = CommonUtils.getCurrentTime();
        save1_tv3.setText(format);
    }

    //请求数据
    private void initData() {
        //reJson={"UserName":"testAccount","Password":"1234","DBName":"ANSA410"}
        SharedPreferences sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sp.getString("username", null);
        String password = sp.getString("password", null);
        String dbName = SPUtils.getString(getApplicationContext(), "DBName");
        String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbName + "\"}";
        Callback callBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();
                getInvInformation = new Gson().fromJson(result, GetInvInformation.class);
                Message message = Message.obtain();
                message.obj = getInvInformation;
                handler.sendMessage(message);

            }
        };
        HttpUtils.getResult(Constant.HOST_GetInvInformation, json, callBack);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {

        private String s;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //点击事务名称的监听
                case R.id.save1_tv1:
                    PopupWindow popupWindow = null;
                    ListView lv1 = new ListView(SaveActivity1.this);
                    if (getInvInformation != null) {
                        transCodeList = getInvInformation.getTransCodeList();
                    }
                    lv1.setAdapter(new TransactionAdapter());
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            save1_tv1.setText(transCodeList.get(position).getTransName());
                            transCode = transCodeList.get(position).getTransCode();
                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv1, 550, 300);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(save1_tv1, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    break;

                //点击从仓库的监听
                case R.id.save1_tv2:
                    popupWindow = null;
                    ListView lv2 = new ListView(SaveActivity1.this);
                    if (getInvInformation != null) {
                        whsList = getInvInformation.getWhsList();
                    }
                    lv2.setAdapter(new CKAdapter());
                    lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            save1_tv2.setText(whsList.get(position).getWhsCode());
                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv2, 550, 300);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(save1_tv2, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    break;

                //点击过账日期的监听
                case R.id.save1_tv3:
                    final Calendar c = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(SaveActivity1.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    c.set(year, monthOfYear, dayOfMonth);
                                    save1_tv3.setText(DateFormat.format("yyy.MM.dd", c));
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    break;

                //点击到仓库的监听
                case R.id.save1_tv4:
                    popupWindow = null;
                    ListView lv4 = new ListView(SaveActivity1.this);
                    if (getInvInformation != null) {
                        whsList = getInvInformation.getWhsList();
                    }
                    lv4.setAdapter(new CKAdapter());
                    lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            save1_tv4.setText(whsList.get(position).getWhsCode());
                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv4, 550, 300);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(save1_tv4, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    break;

                //添加一行图标的点击监听
                case R.id.ac_iv_add:
                    lines.add(new SaveLine());
                    ArrayList list = new ArrayList();
                    lists.add(list);
                    myAdapter.notifyDataSetChanged();
                    break;

                //添加按钮的监听
                case R.id.save1_add:
                    new AlertDialog.Builder(SaveActivity1.this).setTitle("提示")//设置对话框标题
                            .setMessage("是否添加直接制单的库存转储?")//设置显示的内容
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //等待动画
                            progressDialog = ProgressDialog.show(SaveActivity1.this, "", "加载中，请稍后……");

                            //连接服务器获取数据
                            SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            final String username = sp.getString("username", null);
                            final String password = sp.getString("password", null);
                            final String dbname = SPUtils.getString(getApplicationContext(), "DBName");
                            String DocDate = save1_tv3.getText().toString();
                            String Comments = save1_bz.getText().toString();
                            String filler = save1_tv2.getText().toString();
                            String toWhs = save1_tv4.getText().toString();

                            //"reJson={""UserName"":""testAccount"", ""Password"":""1234"", ""DBName"":""ANSA410"",""DocDate"":""2016.12.27"",""TrsCode"":""101"",""Comments"":""haode"",""Filler"":""105"",""ToWhs"":""102"",""Lines"":[{""ItemCode"":""AN00021"",""Amount"":3.0,""BagAmount"":1.0,""Factor2"":1.0,""DocEntry"":2,""LineNum"":0,""Batches"":[{""BatchNumber"":""2016051301"",""Quantity"":3.0}]}]} "
                            String s2 = "";
                            for (SaveLine line : lines) {
                                String s1 = new Gson().toJson(line);
                                s2 += (s1 + ",");
                            }
                            if (s2 != "") {
                                String substring = s2.substring(0, s2.length() - 1);
                                s = "[" + substring + "]";
                            }
                            final String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbname + "\"," +
                                    "\"DocDate\":\"" + DocDate + "\",\"TrsCode\":\"" + transCode + "\",\"Comments\":\"" + Comments + "\"," +
                                    "\"Filler\":\"" + filler + "\",\"ToWhs\":\"" + toWhs + "\",\"Lines\":" + s + "}";
                            Callback callBack = new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    progressDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(SaveActivity1.this).setTitle("添加失败:").setMessage("没有连接到服务器").setNegativeButton("取消", null).show();
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
                                        CommonUtils.write("库存转储直接制单:", json);
                                        progressDialog.dismiss();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(SaveActivity1.this).setTitle("添加失败:").setMessage(Description).setNegativeButton("取消", null).show();
                                            }
                                        });
                                    }
                                }
                            };
                            HttpUtils.getResult(Constant.HOST_AddInventoryTransfer, json, callBack);

                        }
                    }).show();

                    break;
            }
        }
    };

    //事务名称的适配器
    private class TransactionAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return transCodeList == null ? 0 : transCodeList.size();
        }

        @Override
        public Object getItem(int position) {
            return transCodeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(SaveActivity1.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setTag(position);
            viewHolder.ac_item2_4.setTag(position);

            int tag = (int) viewHolder.ac_item2_3.getTag();
            viewHolder.ac_item2_3.setText(transCodeList.get(tag).getTransCode());
            viewHolder.ac_item2_4.setText(transCodeList.get(tag).getTransName());
            return convertView;
        }

        class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //仓库的适配器
    private class CKAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return whsList == null ? 0 : whsList.size();
        }

        @Override
        public Object getItem(int position) {
            return whsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(SaveActivity1.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setTag(position);
            viewHolder.ac_item2_4.setTag(position);

            int tag = (int) viewHolder.ac_item2_3.getTag();
            viewHolder.ac_item2_3.setText(whsList.get(tag).getWhsCode());
            viewHolder.ac_item2_4.setText(whsList.get(tag).getWhsName());
            return convertView;
        }

        class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //行信息listview的适配器
    public class MyAdapter extends BaseAdapter {

        private final List<SaveLine> lines;

        public MyAdapter(List<SaveLine> lines) {
            this.lines = lines;
        }

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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            double sum1 = 0.0;
            double sum2 = 0.0;
            for (int i = 0; i <= position; i++) {
                sum1 += lines.get(i).getBagAmount();
                sum2 += lines.get(i).getAmount();
            }
            save1_tv5.setText(String.valueOf(sum1));
            save1_tv6.setText(CommonUtils.addComma(String.valueOf(sum2)));
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(SaveActivity1.this, R.layout.ac_item4_1, null);
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

            holder.ac_item4_1.setText(lines.get(position).getItemCode());
            holder.ac_item4_3.setText(String.valueOf(lines.get(position).getBagAmount()));
            holder.ac_item4_4.setText(CommonUtils.addComma(String.valueOf(lines.get(position).getAmount())));
            holder.ac_item4_5.setText("可选 ->");

            holder.ac_item_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lines.remove(position);
                    lists.remove(position);
                    notifyDataSetChanged();
                }
            });

            holder.ac_item4_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int tag = (int) holder.ac_item4_1.getTag();
                    ListView lv5 = new ListView(SaveActivity1.this);
                    LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                    View view = layoutInflater.inflate(R.layout.item_search, null);
                    lv5.addHeaderView(view);
                    if (getInvInformation != null) {
                        itemList = getInvInformation.getItemList();
                        list = itemList;
                    }
                    final EditText item_search = (EditText) view.findViewById(R.id.item_search);
                    item_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            String trim = item_search.getText().toString().trim();
                            list = new ArrayList<GetInvInformation.ItemListbean>();
                            for (int i = 0; i < itemList.size(); i++) {
                                if ((itemList.get(i).getItemCode() + itemList.get(i).getItemName()).contains(trim)) {
                                    GetInvInformation.ItemListbean itemListbean = itemList.get(i);
                                    list.add(itemListbean);
                                }
                            }
                            bmAdapter.notifyDataSetChanged();
                            return false;
                        }
                    });
                    bmAdapter = new BMAdapter();
                    lv5.setAdapter(bmAdapter);
                    lv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        private int num;

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            holder.ac_item4_1.setText(list.get(position - 1).getItemCode());
                            holder.ac_item4_2.setText(list.get(position - 1).getItemName());
                            lines.get(tag).setItemCode(holder.ac_item4_1.getText().toString());
                            for (int i = 0; i < itemList.size(); i++) {
                                if (itemList.get(i).getItemCode().equals(holder.ac_item4_1.getText().toString())) {
                                    num = i;
                                }
                            }
                            qtyInWhs = itemList.get(num).getQtyInWhs();
                            lines.get(tag).setBatches(new ArrayList<SaveLine.Batchesbean>());
                            lists.get(tag).clear();
                            for (GetInvInformation.ItemListbean.QtyInWhsbean qtyInWh : qtyInWhs) {
                                if (qtyInWh.getWhsCode().equals(save1_tv2.getText().toString())) {
                                    lists.get(tag).add(qtyInWh);
                                }
                            }
                            if (lists.size() != 0) {
                                for (int i = 0; i < lists.get(tag).size(); i++) {
                                    lines.get(tag).getBatches().add(new SaveLine.Batchesbean());
                                }
                            }
                        }
                    });
                    PopupWindow popupWindow = null;
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv5, 800, 400);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(holder.ac_item4_1, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                }
            });

            holder.ac_item4_3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = holder.ac_item4_3.getText().toString().trim();
                    int tag = (int) holder.ac_item4_3.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    lines.get(tag).setBagAmount(Double.parseDouble(trim));
                    lines.get(tag).setFactor2(Double.parseDouble(trim));
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
                    int tag = (int) holder.ac_item4_4.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    lines.get(tag).setAmount(Double.parseDouble(trim));
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
                    ListView lv6 = new ListView(SaveActivity1.this);
                    int tag = (int) holder.ac_item4_5.getTag();
                    if (lists.size() != 0) {
                        List<GetInvInformation.ItemListbean.QtyInWhsbean> list = lists.get(tag);
                        lv6.setAdapter(new PCAdapter(list, tag));
                    }

                    PopupWindow popupWindow = null;
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv6, 550, 300);
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
    }

    //编码列表适配器
    private class BMAdapter extends BaseAdapter {
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
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(SaveActivity1.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setTag(position);
            viewHolder.ac_item2_4.setTag(position);

            int tag = (int) viewHolder.ac_item2_3.getTag();
            viewHolder.ac_item2_3.setText(list.get(tag).getItemCode());
            viewHolder.ac_item2_4.setText(list.get(tag).getItemName());
            return convertView;
        }

        class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //内部批次listview的适配器
    private class PCAdapter extends BaseAdapter {

        private final List<GetInvInformation.ItemListbean.QtyInWhsbean> list;
        private final int tag;

        public PCAdapter(List<GetInvInformation.ItemListbean.QtyInWhsbean> list, int tag) {
            this.list = list;
            this.tag = tag;
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
                convertView = View.inflate(SaveActivity1.this, R.layout.ac_item2_3, null);
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

            batches = lines.get(tag).getBatches();
            viewHolder.ac_item2_3.setText(list.get(position).getBatchNumber());
            viewHolder.ac_item2_4.setText(String.valueOf(list.get(position).getQuanity()));
            viewHolder.ac_item2_5.setText(String.valueOf(list.get(position).getAmount()));
            viewHolder.ac_item2_5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = viewHolder.ac_item2_5.getText().toString().trim();
                    int pcTag = (int) viewHolder.ac_item2_5.getTag();

                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    list.get(pcTag).setBatchNumber(viewHolder.ac_item2_3.getText().toString());
                    list.get(pcTag).setAmount(Double.parseDouble(trim));
                    batches.get(pcTag).setBatchNumber(list.get(pcTag).getBatchNumber());
                    batches.get(pcTag).setQuantity(list.get(pcTag).getAmount());
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
