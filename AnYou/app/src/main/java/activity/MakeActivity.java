package activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import bean.GetOpenProductionDetail;
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
public class MakeActivity extends BaseActivity {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private EditText tv4;
    private EditText tv5;
    private TextView tv6;
    private EditText tv7;
    private Button bt_add3;
    private GetOpenProductionDetail getOpenProductionDetail;
    private ProgressDialog progressDialog;
    private String objectAuth_590;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            tv2.setText(getOpenProductionDetail.getItemCode());
            tv3.setText(getOpenProductionDetail.getItemName());
            tv4.setText(String.valueOf(getOpenProductionDetail.getBagAmount()));
            tv4.setSelection(tv4.getText().length());
            tv6.setText(getOpenProductionDetail.getWhsCode());
            tv7.setText(getOpenProductionDetail.getBatchNumber());

        }
    };

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_make;
    }

    @Override
    public void initView(final View v) {
        tv1 = ((TextView) findViewById(R.id.make_tv1));
        tv2 = ((TextView) findViewById(R.id.make_tv2));
        tv3 = ((TextView) findViewById(R.id.make_tv3));
        tv4 = ((EditText) findViewById(R.id.make_tv4));
        tv5 = ((EditText) findViewById(R.id.make_tv5));
        tv6 = ((TextView) findViewById(R.id.make_tv6));
        tv7 = ((EditText) findViewById(R.id.make_tv7));
        bt_add3 = ((Button) findViewById(R.id.bt_add3));

        objectAuth_590 = SPUtils.getString(getApplicationContext(), "ObjectAuth_590");
        if (objectAuth_590.equals("R")) {
            bt_add3.setVisibility(View.GONE);
            int i = Color.parseColor("#E4E4E4");
            tv1.setBackgroundColor(i);
            tv4.setBackgroundColor(i);
            tv5.setBackgroundColor(i);
            tv7.setBackgroundColor(i);
            tv1.setEnabled(false);
            tv4.setEnabled(false);
            tv5.setEnabled(false);
            tv7.setEnabled(false);
        }

        initTime();
        //reJson={"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410","DocEntry":41}
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
                getOpenProductionDetail = new Gson().fromJson(result, GetOpenProductionDetail.class);
                Message message = Message.obtain();
                message.obj = getOpenProductionDetail;
                handler.sendMessage(message);
            }
        };
        HttpUtils.getResult(Constant.HOST_GetOpenProductionDetail, json, callBack);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(MakeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                c.set(year, monthOfYear, dayOfMonth);
                                tv1.setText(DateFormat.format("yyy.MM.dd", c));
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        tv4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String trim = tv4.getText().toString().trim();
                if (trim == null || trim.length() == 0) {
                    trim = "0.0";
                }
                double v1 = Double.parseDouble(trim);
                double v2 = getOpenProductionDetail.getFactor1();
                double v3 = CommonUtils.mul(v1, v2);
                tv5.setText(CommonUtils.addComma(String.valueOf(v3)));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tv5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!CommonUtils.touzi_ed_values22.equals(tv5.getText().toString().trim().replace(",", ""))) {
                    tv5.setText(CommonUtils.addCommas(tv5.getText().toString().trim().replace(",", ""), tv5));
                    tv5.setSelection(CommonUtils.addCommas(tv5.getText().toString().trim().replace(",", ""), tv5).length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bt_add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MakeActivity.this).setTitle("提示")//设置对话框标题
                        .setMessage("是否添加生产收货单?")//设置显示的内容
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //等待动画
                        progressDialog = ProgressDialog.show(MakeActivity.this, "", "正在添加生产收货单，请稍后……");

                        SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        String username = sp.getString("username", null);
                        String password = sp.getString("password", null);
                        String dbname = SPUtils.getString(getApplicationContext(), "DBName");
                        Intent intent = getIntent();
                        String docEntry = intent.getStringExtra("docEntry");
                        Integer integer = Integer.decode(docEntry);
                        String DocDate = tv1.getText().toString().trim();
                        String ItemCode = tv2.getText().toString().trim();
                        String s1 = tv4.getText().toString().trim();
                        Double BagAmount = Double.valueOf(s1);
                        String s2 = tv5.getText().toString().trim().replace(",", "");
                        Double Quantity = Double.valueOf(s2);
                        String WhsCode = tv6.getText().toString().trim();
                        String BatchNumber = tv7.getText().toString().trim();

                        //reJson={"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410","BaseEntry":40,"DocDate":"2016.12.22","ItemCode":"ZNF404011NAY05","BagAmount":10,"Quantity":200,"WhsCode":"401","BatchNumber":"2016122201"}
                        final String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbname + "\",\"BaseEntry\":" + integer + "," +
                                "\"DocDate\":\"" + DocDate + "\",\"ItemCode\":\"" + ItemCode + "\"," + "\"BagAmount\":" + BagAmount + ",\"Quantity\":" + Quantity + "," +
                                "\"WhsCode\":\"" + WhsCode + "\",\"BatchNumber\":\"" + BatchNumber + "\"}";
                        Callback callBack = new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                progressDialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(MakeActivity.this).setTitle("添加失败:").setMessage("没有连接到服务器").setNegativeButton("取消", null).show();
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
                                    CommonUtils.write("生产收货:", json);
                                    progressDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(MakeActivity.this).setTitle("添加失败:").setMessage(Description).setNegativeButton("取消", null).show();
                                        }
                                    });
                                }
                            }
                        };
                        HttpUtils.getResult(Constant.HOST_AddProductionReceive, json, callBack);

                    }
                }).show();

            }
        });

    }

    //默认显示当天时间
    private void initTime() {
        String format = CommonUtils.getCurrentTime();
        tv1.setText(format);
    }

}
