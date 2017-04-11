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

import bean.SearchSOReturnDetail;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2017/1/19 0019.
 */
public class SellReturnActivity2 extends BaseActivity {

    private TextView buyreturn2_tv1;
    private TextView buyreturn2_tv2;
    private TextView buyreturn2_tv3;
    private ListView buyreturn2_lv;
    private TextView buyreturn2_tv4;
    private TextView buyreturn2_tv5;
    private EditText buyreturn2_bz;
    private Button buyreturn2_add;
    private SearchSOReturnDetail searchSOReturnDetail;
    private List<SearchSOReturnDetail.Linesbean> lines;
    private List<SearchSOReturnDetail.Linesbean.ReWhsbean> reWhs;
    private List<SearchSOReturnDetail.Linesbean.ReWhsbean.ChooseBatchesbean> chooseBatches;
    private ProgressDialog progressDialog;
    private String objectAuth_16;

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_sellreturn2;
    }

    @Override
    public void initView(View v) {
        Intent intent = getIntent();
        String result = intent.getStringExtra("searchSOReturnDetail");
        searchSOReturnDetail = new Gson().fromJson(result, SearchSOReturnDetail.class);
        lines = searchSOReturnDetail.getLines();

        buyreturn2_tv1 = ((TextView) findViewById(R.id.buyreturn2_tv1));
        buyreturn2_tv2 = ((TextView) findViewById(R.id.buyreturn2_tv2));
        buyreturn2_tv3 = ((TextView) findViewById(R.id.buyreturn2_tv3));
        buyreturn2_lv = ((ListView) findViewById(R.id.buyreturn2_lv));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.item_foot_buyreturn2, null);
        buyreturn2_lv.addFooterView(view);
        buyreturn2_lv.setFooterDividersEnabled(false);
        buyreturn2_tv4 = ((TextView) view.findViewById(R.id.buyreturn2_tv4));
        buyreturn2_tv5 = ((TextView) view.findViewById(R.id.buyreturn2_tv5));
        buyreturn2_bz = ((EditText) view.findViewById(R.id.buyreturn2_bz));
        buyreturn2_add = ((Button) view.findViewById(R.id.buyreturn2_add));

        objectAuth_16 = SPUtils.getString(getApplicationContext(), "ObjectAuth_16");
        if (objectAuth_16.equals("R")) {
            buyreturn2_add.setVisibility(View.GONE);
            buyreturn2_tv1.setBackgroundResource(R.drawable.bg_nochecked);
            buyreturn2_tv1.setEnabled(false);
            buyreturn2_tv2.setBackgroundResource(R.drawable.bg_nochecked);
            buyreturn2_tv2.setEnabled(false);
            int i = Color.parseColor("#EBEFF1");
            buyreturn2_bz.setBackgroundColor(i);
            buyreturn2_bz.setEnabled(false);
        }

        initTime();
        buyreturn2_tv1.setText(searchSOReturnDetail.getCardCode());
        buyreturn2_tv3.setText(searchSOReturnDetail.getCardName());
        buyreturn2_tv2.setOnClickListener(onClick);
        buyreturn2_add.setOnClickListener(onClick);
        buyreturn2_lv.setAdapter(new MyAdapter());
    }

    //默认显示当天时间
    private void initTime() {
        String format = CommonUtils.getCurrentTime();
        buyreturn2_tv2.setText(format);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //点击过账日期的监听
                case R.id.buyreturn2_tv2:
                    final Calendar c = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(SellReturnActivity2.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    c.set(year, monthOfYear, dayOfMonth);
                                    buyreturn2_tv2.setText(DateFormat.format("yyy.MM.dd", c));
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    break;

                //添加按钮的监听
                case R.id.buyreturn2_add:
                    new AlertDialog.Builder(SellReturnActivity2.this).setTitle("提示")//设置对话框标题
                            .setMessage("是否添加销售退货的未清单据?")//设置显示的内容
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //等待动画
                            progressDialog = ProgressDialog.show(SellReturnActivity2.this, "", "加载中，请稍后……");

                            //连接服务器获取数据
                            SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            final String username = sp.getString("username", null);
                            final String password = sp.getString("password", null);
                            final String dbname = SPUtils.getString(getApplicationContext(), "DBName");
                            String CardCode = buyreturn2_tv1.getText().toString();
                            String CardName = buyreturn2_tv3.getText().toString();
                            String Comments = buyreturn2_bz.getText().toString();
                            String DocDate = buyreturn2_tv2.getText().toString();

                            //{"UserName":"testAccount","Password":"1234","DBName":"ANSA410","CardCode":"S00015","CardName":"温州新吉高包装有限公司","DocDate":"2016.12.30","Lines":[]}
                            String s2 = "";
                            for (SearchSOReturnDetail.Linesbean line : lines) {
                                line.setReWhs(null);
                                String s1 = new Gson().toJson(line);
                                s2 += (s1 + ",");
                            }
                            String substring = s2.substring(0, s2.length() - 1);
                            String s = "[" + substring + "]";
                            final String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbname +
                                    "\", \"CardCode\":\"" + CardCode + "\", \"CardName\":\"" + CardName + "\",\"DocDate\":\"" + DocDate + "\",\"Comments\":\"" + Comments + "\",\"Lines\":" + s + "}";
                            Callback callBack = new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    progressDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(SellReturnActivity2.this).setTitle("添加失败:").setMessage("没有连接到服务器").setNegativeButton("取消", null).show();
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
                                        CommonUtils.write("销售退货查找:", json);
                                        progressDialog.dismiss();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(SellReturnActivity2.this).setTitle("添加失败:").setMessage(Description).setNegativeButton("取消", null).show();
                                            }
                                        });
                                    }
                                }
                            };
                            HttpUtils.getResult(Constant.HOST_AddSOReturn, json, callBack);

                        }
                    }).show();

                    break;
            }
        }
    };

    //外部listview的适配器
    private class MyAdapter extends BaseAdapter {
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
            for (int i = 0; i <= position; i++) {
                sum1 += lines.get(i).getBagAmount();
                sum2 += lines.get(i).getAmount();
            }
            buyreturn2_tv4.setText(String.valueOf(sum1));
            buyreturn2_tv5.setText(CommonUtils.addComma(String.valueOf(sum2)));
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(SellReturnActivity2.this, R.layout.ac_item5_1, null);
                holder.ac_item5_iv = (ImageView) convertView.findViewById(R.id.ac_item5_iv);
                holder.ac_item5_1 = ((TextView) convertView.findViewById(R.id.ac_item5_1));
                holder.ac_item5_2 = ((TextView) convertView.findViewById(R.id.ac_item5_2));
                holder.ac_item5_3 = ((EditText) convertView.findViewById(R.id.ac_item5_3));
                holder.ac_item5_4 = ((EditText) convertView.findViewById(R.id.ac_item5_4));
                holder.ac_item5_5 = ((TextView) convertView.findViewById(R.id.ac_item5_5));
                holder.ac_item5_6 = ((TextView) convertView.findViewById(R.id.ac_item5_6));
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }
            holder.ac_item5_iv.setTag(position);
            holder.ac_item5_1.setTag(position);
            holder.ac_item5_2.setTag(position);
            holder.ac_item5_3.setTag(position);
            holder.ac_item5_4.setTag(position);
            holder.ac_item5_5.setTag(position);
            holder.ac_item5_6.setTag(position);

            holder.ac_item5_1.setText(lines.get(position).getItemCode());
            holder.ac_item5_2.setText(lines.get(position).getItemCode());
            holder.ac_item5_3.setText(String.valueOf(lines.get(position).getBagAmount()));
            holder.ac_item5_4.setText(CommonUtils.addComma(String.valueOf(lines.get(position).getAmount())));
            holder.ac_item5_5.setText(lines.get(position).getWhsCode());
            holder.ac_item5_6.setText("可选 ->");

            if (objectAuth_16.equals("R")) {
                holder.ac_item5_iv.setVisibility(View.INVISIBLE);
                int i = Color.parseColor("#EBEFF1");
                holder.ac_item5_3.setBackgroundColor(i);
                holder.ac_item5_4.setBackgroundColor(i);
                holder.ac_item5_5.setBackgroundColor(i);
                holder.ac_item5_6.setBackgroundColor(i);
                holder.ac_item5_3.setEnabled(false);
                holder.ac_item5_4.setEnabled(false);
                holder.ac_item5_5.setEnabled(false);
                holder.ac_item5_6.setEnabled(false);
            }

            holder.ac_item5_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lines.remove(position);
                    notifyDataSetChanged();
                }
            });

            holder.ac_item5_3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = holder.ac_item5_3.getText().toString().trim();
                    int tag = (int) holder.ac_item5_3.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    lines.get(tag).setBagAmount(Double.parseDouble(trim));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item5_4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = holder.ac_item5_4.getText().toString().trim().replace(",", "");
                    int tag = (int) holder.ac_item5_4.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    lines.get(tag).setAmount(Double.parseDouble(trim));
                    lines.get(tag).setFactor2(Double.parseDouble(trim));
                    if (!CommonUtils.touzi_ed_values22.equals(holder.ac_item5_4.getText().toString().trim().replace(",", ""))) {
                        holder.ac_item5_4.setText(CommonUtils.addCommas(holder.ac_item5_4.getText().toString().trim().replace(",", ""), holder.ac_item5_4));
                        holder.ac_item5_4.setSelection(CommonUtils.addCommas(holder.ac_item5_4.getText().toString().trim().replace(",", ""), holder.ac_item5_4).length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            reWhs = lines.get(position).getReWhs();
            for (SearchSOReturnDetail.Linesbean.ReWhsbean reWh : reWhs) {
                if (reWh.getWhsCode().equals(holder.ac_item5_5.getText().toString())) {
                    chooseBatches = reWh.getChooseBatches();
                    for (SearchSOReturnDetail.Linesbean.ReWhsbean.ChooseBatchesbean chooseBatch : chooseBatches) {
                        chooseBatch.setWhsCode(null);
                        chooseBatch.setSysNumber(null);
                        chooseBatch.setItemCode(null);
                    }
                    lines.get(position).setBatches(chooseBatches);
                }
            }
            holder.ac_item5_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int tag = (int) holder.ac_item5_5.getTag();
                    reWhs = lines.get(tag).getReWhs();
                    ListView lv2 = new ListView(SellReturnActivity2.this);
                    lv2.setAdapter(new CKAdapter());
                    lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            holder.ac_item5_5.setText(SellReturnActivity2.this.reWhs.get(position).getWhsCode());
                            lines.get(tag).setWhsCode(holder.ac_item5_5.getText().toString());
                            chooseBatches = reWhs.get(position).getChooseBatches();
                            for (SearchSOReturnDetail.Linesbean.ReWhsbean.ChooseBatchesbean chooseBatch : chooseBatches) {
                                chooseBatch.setWhsCode(null);
                                chooseBatch.setSysNumber(null);
                                chooseBatch.setItemCode(null);
                            }
                            lines.get(tag).setBatches(chooseBatches);
                        }
                    });
                    PopupWindow popupWindow = null;
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv2, 500, 300);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(holder.ac_item5_5, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                }
            });

            holder.ac_item5_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListView lv3 = new ListView(SellReturnActivity2.this);
                    final int tag = (int) holder.ac_item5_6.getTag();
                    chooseBatches = (List<SearchSOReturnDetail.Linesbean.ReWhsbean.ChooseBatchesbean>) lines.get(tag).getBatches();
                    lv3.setAdapter(new PCAdapter());

                    PopupWindow popupWindow = null;
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv3, 450, 300);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(holder.ac_item5_6, 0, 0);
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
            ImageView ac_item5_iv;
            TextView ac_item5_1;
            TextView ac_item5_2;
            EditText ac_item5_3;
            EditText ac_item5_4;
            TextView ac_item5_5;
            TextView ac_item5_6;
        }
    }

    //内部仓库listview的适配器
    private class CKAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reWhs == null ? 0 : reWhs.size();
        }

        @Override
        public Object getItem(int position) {
            return reWhs.get(position);
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
                convertView = View.inflate(SellReturnActivity2.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }

            viewHolder.ac_item2_3.setText(reWhs.get(position).getWhsName());
            viewHolder.ac_item2_4.setText(reWhs.get(position).getWhsCode());
            return convertView;
        }

        class ViewHolder {

            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //内部批次listview的适配器
    private class PCAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return chooseBatches == null ? 0 : chooseBatches.size();
        }

        @Override
        public Object getItem(int position) {
            return chooseBatches.get(position);
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
                convertView = View.inflate(SellReturnActivity2.this, R.layout.ac_item2_4, null);
                viewHolder.ac_item2_3 = (EditText) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (EditText) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setTag(position);
            viewHolder.ac_item2_4.setTag(position);

            viewHolder.ac_item2_3.setText(chooseBatches.get(position).getBatchNumber());
            viewHolder.ac_item2_4.setText(String.valueOf(chooseBatches.get(position).getAmount()));
            viewHolder.ac_item2_3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = viewHolder.ac_item2_3.getText().toString().trim();
                    int pcTag = (int) viewHolder.ac_item2_3.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0";
                    }
                    chooseBatches.get(pcTag).setBatchNumber(trim);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            viewHolder.ac_item2_4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = viewHolder.ac_item2_4.getText().toString().trim();
                    int pcTag = (int) viewHolder.ac_item2_4.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0";
                    }
                    chooseBatches.get(pcTag).setAmount(Double.parseDouble(trim));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            return convertView;
        }

        class ViewHolder {

            EditText ac_item2_3;
            EditText ac_item2_4;
        }

    }

}
