package activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import bean.GetInventoryReceiptInfo;
import bean.InventoryReceiptLine;
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
public class InventoryReceiptActivity extends BaseActivity {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private EditText tv8;
    private ListView lv;
    private ImageView ac_iv_add;
    private TextView total_tv4;
    private TextView total_tv6;
    private EditText total_bz;
    private Button total_add;
    private MyAdapter myAdapter;
    private List<InventoryReceiptLine> lines;
    private GetInventoryReceiptInfo getInventoryReceiptInfo;
    private List<GetInventoryReceiptInfo.TransInfobean> transInfo;
    private List<GetInventoryReceiptInfo.WhsInfobean> whsInfo;
    private List<GetInventoryReceiptInfo.UseDepartbean> useDepart;
    private List<GetInventoryReceiptInfo.BaseDocTypebean> baseDocType;
    private List<GetInventoryReceiptInfo.InDepartbean> inDepart;
    private ProgressDialog progressDialog;
    private String substring;
    private List<GetInventoryReceiptInfo.Itembean> list;
    private BMAdapter bmAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                progressDialog.dismiss();
                CommonUtils.showToast(getApplicationContext(), "数据加载完毕");
            }
            myAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_inventoryreceipt;
    }

    @Override
    public void initView(View v) {
        progressDialog = ProgressDialog.show(InventoryReceiptActivity.this, "", "数据加载中，请稍后……");
        progressDialog.setCancelable(true);
        tv1 = ((TextView) findViewById(R.id.tv1));
        tv2 = ((TextView) findViewById(R.id.tv2));
        tv3 = ((TextView) findViewById(R.id.tv3));
        tv4 = ((TextView) findViewById(R.id.tv4));
        tv5 = ((TextView) findViewById(R.id.tv5));
        tv6 = ((TextView) findViewById(R.id.tv6));
        tv7 = ((TextView) findViewById(R.id.tv7));
        tv8 = ((EditText) findViewById(R.id.tv8));
        lv = ((ListView) findViewById(R.id.lv));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.item_foot_receipt, null);
        lv.addFooterView(view);
        lv.setFooterDividersEnabled(false);
        ac_iv_add = ((ImageView) view.findViewById(R.id.ac_iv_add));
        total_tv4 = ((TextView) view.findViewById(R.id.buyreturn_tv4));
        total_tv6 = ((TextView) view.findViewById(R.id.buyreturn_tv6));
        total_bz = ((EditText) view.findViewById(R.id.buyreturn_bz));
        total_add = ((Button) view.findViewById(R.id.buyreturn_add));

        initTime();
        initData();
        tv1.setOnClickListener(onClick);
        tv2.setOnClickListener(onClick);
        tv3.setOnClickListener(onClick);
        tv4.setOnClickListener(onClick);
        tv5.setOnClickListener(onClick);
        tv6.setOnClickListener(onClick);
        tv7.setOnClickListener(onClick);
        ac_iv_add.setOnClickListener(onClick);
        total_add.setOnClickListener(onClick);

        lines = new ArrayList<>();
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
    }

    //默认显示当天时间
    private void initTime() {
        String format = CommonUtils.getCurrentTime();
        tv3.setText(format);
    }

    //请求数据
    private void initData() {
        //reJson={"UserName":"testAccount", "Password":"1234", "DBName":"ANSA410"}
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
                getInventoryReceiptInfo = new Gson().fromJson(result, GetInventoryReceiptInfo.class);
                Message message = Message.obtain();
                message.obj = getInventoryReceiptInfo;
                handler.sendMessage(message);

            }
        };
        HttpUtils.getResult(Constant.HOST_GetInventoryReceiptInfo, json, callBack);
    }


    private View.OnClickListener onClick = new View.OnClickListener() {

        private String typeCode;
        private String inDepartCode;
        private String useDepartCode;
        private String trsCode;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //事务名称的监听
                case R.id.tv1:
                    PopupWindow popupWindow = null;
                    ListView lv1 = new ListView(InventoryReceiptActivity.this);
                    LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                    View view = layoutInflater.inflate(R.layout.item_blank, null);
                    lv1.addHeaderView(view);
                    if (getInventoryReceiptInfo != null) {
                        transInfo = getInventoryReceiptInfo.getTransInfo();
                    }
                    lv1.setAdapter(new TransInfoAdapter());
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                tv1.setText("");
                            } else {
                                tv1.setText(transInfo.get(position - 1).getTrsName());
                                trsCode = transInfo.get(position - 1).getTrsCode();
                            }
                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv1, 600, 400);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(tv1, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    break;

                //到仓库的监听
                case R.id.tv2:
                    popupWindow = null;
                    ListView lv2 = new ListView(InventoryReceiptActivity.this);
                    layoutInflater = LayoutInflater.from(getApplicationContext());
                    view = layoutInflater.inflate(R.layout.item_blank, null);
                    lv2.addHeaderView(view);
                    if (getInventoryReceiptInfo != null) {
                        whsInfo = getInventoryReceiptInfo.getWhsInfo();
                    }
                    lv2.setAdapter(new WhsInfoAdapter());
                    lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                tv2.setText("");
                            } else {
                                tv2.setText(whsInfo.get(position - 1).getWhsCode());
                            }
                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv2, 600, 400);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(tv2, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    break;

                //过账日期的监听
                case R.id.tv3:
                    final Calendar c = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(InventoryReceiptActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    c.set(year, monthOfYear, dayOfMonth);
                                    tv3.setText(DateFormat.format("yyy.MM.dd", c));
                                }
                            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                    break;

                //领用部门的监听
                case R.id.tv4:
                    popupWindow = null;
                    ListView lv4 = new ListView(InventoryReceiptActivity.this);
                    layoutInflater = LayoutInflater.from(getApplicationContext());
                    view = layoutInflater.inflate(R.layout.item_blank, null);
                    lv4.addHeaderView(view);
                    if (getInventoryReceiptInfo != null) {
                        useDepart = getInventoryReceiptInfo.getUseDepart();
                    }
                    lv4.setAdapter(new UseDepartAdapter());
                    lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                tv4.setText("");
                            } else {
                                tv4.setText(useDepart.get(position - 1).getUseDepartName());
                                useDepartCode = useDepart.get(position - 1).getUseDepartCode();
                            }

                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv4, 600, 400);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(tv4, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    break;

                //入库部门的监听
                case R.id.tv5:
                    popupWindow = null;
                    ListView lv5 = new ListView(InventoryReceiptActivity.this);
                    layoutInflater = LayoutInflater.from(getApplicationContext());
                    view = layoutInflater.inflate(R.layout.item_blank, null);
                    lv5.addHeaderView(view);
                    if (getInventoryReceiptInfo != null) {
                        inDepart = getInventoryReceiptInfo.getInDepart();
                    }
                    lv5.setAdapter(new InDepartAdapter());
                    lv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                tv5.setText("");
                            } else {
                                tv5.setText(inDepart.get(position - 1).getInDepartName());
                                inDepartCode = inDepart.get(position - 1).getInDepartCode();
                            }
                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv5, 600, 400);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(tv5, 0, 0);
                        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                    break;

                //成本中心
                case R.id.tv6:
                    popupWindow = null;
                    ListView lv6 = new ListView(InventoryReceiptActivity.this);
                    layoutInflater = LayoutInflater.from(getApplicationContext());
                    view = layoutInflater.inflate(R.layout.item_blank, null);
                    lv6.addHeaderView(view);
                    if (getInventoryReceiptInfo != null) {
                        final List<GetInventoryReceiptInfo.CostCenterbean> list = getInventoryReceiptInfo.getCostCenter();
//                        final List<GetInventoryReceiptInfo.CostCenterbean> list = new ArrayList();
//                        for (GetInventoryReceiptInfo.CostCenterbean costCenterbean : costCenter) {
//                            if (costCenterbean.getTrsName().equals(tv1.getText().toString())) {
//                                list.add(costCenterbean);
//                            }
//                        }
                        lv6.setAdapter(new CostCenterAdapter(list));
                        lv6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    tv6.setText("");
                                } else {
                                    tv6.setText(list.get(position - 1).getCostCenterCode());
                                }

                            }
                        });

                        if (popupWindow == null) {
                            popupWindow = new PopupWindow(lv6, 600, 400);
                            popupWindow.setFocusable(true);
                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                            popupWindow.setOutsideTouchable(true);

                        }
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        } else {
                            popupWindow.showAsDropDown(tv6, 0, 0);
                            popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }
                            });
                        }
                    }
                    break;

                //源单类型监听
                case R.id.tv7:
                    popupWindow = null;
                    ListView lv7 = new ListView(InventoryReceiptActivity.this);
                    layoutInflater = LayoutInflater.from(getApplicationContext());
                    view = layoutInflater.inflate(R.layout.item_blank, null);
                    lv7.addHeaderView(view);
                    if (getInventoryReceiptInfo != null) {
                        baseDocType = getInventoryReceiptInfo.getBaseDocType();
                    }
                    lv7.setAdapter(new BaseDocTypeAdapter());
                    lv7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                tv7.setText("");
                            } else {
                                tv7.setText(baseDocType.get(position - 1).getTypeName());
                                typeCode = baseDocType.get(position - 1).getTypeCode();
                            }

                        }
                    });

                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(lv7, 600, 400);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);

                    }
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else {
                        popupWindow.showAsDropDown(tv7, 0, 0);
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
                    lines.add(new InventoryReceiptLine());
                    myAdapter.notifyDataSetChanged();
                    if (getInventoryReceiptInfo != null) {
                        String str = getInventoryReceiptInfo.getRecommendBatches();
                        int i = Integer.parseInt(str.substring(str.length() - 2)) + lines.size() - 1;
                        String s;
                        if (i < 10) {
                            s = str.substring(0, str.length() - 2) + "0" + String.valueOf(i);
                        } else {
                            s = str.substring(0, str.length() - 2) + String.valueOf(i);
                        }
                        lines.get(lines.size() - 1).setBatchNumber(s);
                    }
                    break;

                //添加按钮的监听
                case R.id.buyreturn_add:
                    new AlertDialog.Builder(InventoryReceiptActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("是否添加库存收货?")//设置显示的内容
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //等待动画
                            progressDialog = ProgressDialog.show(InventoryReceiptActivity.this, "", "加载中，请稍后……");

                            //连接服务器获取数据
                            SharedPreferences sp = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            final String username = sp.getString("username", null);
                            final String password = sp.getString("password", null);
                            final String dbname = SPUtils.getString(getApplicationContext(), "DBName");
                            String ToWhsCode = tv2.getText().toString();
                            String DocDate = tv3.getText().toString();
                            String CostCenter = tv6.getText().toString();
                            String Comments = total_bz.getText().toString().trim();
                            String BaseDocNum = tv8.getText().toString().trim();

//                            {DBName": "ANASA410","UserName": "testAccount","Password": "1234","TrsCode": "302", "ToWhsCode": "401","DocDate": "2017.01.16",
//                                    "UseDepartCode": "3001005","InDepartCode": "","CostCenter": "","BaseDocType": "202","BaseDocNum": "26","Comments": "test",
//                                    "Lines": s }
                            String s2 = "";
                            for (InventoryReceiptLine line : lines) {
                                line.setItemName(null);
                                String s1 = new Gson().toJson(line);
                                s2 += (s1 + ",");
                            }
                            if (s2.length() != 0) {
                                substring = s2.substring(0, s2.length() - 1);
                            }
                            String s = "[" + substring + "]";
                            final String json = "{\"UserName\":\"" + username + "\", \"Password\":\"" + password + "\", \"DBName\":\"" + dbname +
                                    "\", \"TrsCode\":\"" + trsCode + "\", \"ToWhsCode\":\"" + ToWhsCode + "\",\"DocDate\":\"" + DocDate +
                                    "\",\"UseDepartCode\":\"" + useDepartCode + "\",\"InDepartCode\":\"" + inDepartCode + "\",\"CostCenter\":\"" + CostCenter +
                                    "\",\"BaseDocType\":\"" + typeCode + "\",\"BaseDocNum\":\"" + BaseDocNum + "\",\"Comments\":\"" + Comments + "\",\"Lines\":" + s + "}";
                            Callback callBack = new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    progressDialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(InventoryReceiptActivity.this).setTitle("添加失败:").setMessage("没有连接到服务器").setNegativeButton("取消", null).show();
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
                                        CommonUtils.write("库存收货:", json);
                                        progressDialog.dismiss();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(InventoryReceiptActivity.this).setTitle("添加失败:").setMessage(Description).setNegativeButton("取消", null).show();
                                            }
                                        });
                                    }
                                }
                            };
                            HttpUtils.getResult(Constant.HOST_AddInventoryReceipt, json, callBack);

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
                sum1 += lines.get(i).getQuantity();
                sum2 += CommonUtils.mul(lines.get(i).getPrice(), lines.get(i).getQuantity());
            }
            total_tv4.setText(CommonUtils.addComma(String.valueOf(sum1)));
            total_tv6.setText(String.valueOf(sum2));
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item5_2, null);
                holder.ac_item5_iv = (ImageView) convertView.findViewById(R.id.ac_item5_iv);
                holder.ac_item5_1 = ((TextView) convertView.findViewById(R.id.ac_item5_1));
                holder.ac_item5_2 = ((TextView) convertView.findViewById(R.id.ac_item5_2));
                holder.ac_item5_3 = ((EditText) convertView.findViewById(R.id.ac_item5_3));
                holder.ac_item5_4 = ((EditText) convertView.findViewById(R.id.ac_item5_4));
                holder.ac_item5_5 = ((TextView) convertView.findViewById(R.id.ac_item5_5));
                holder.ac_item5_6 = ((EditText) convertView.findViewById(R.id.ac_item5_6));
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
            holder.ac_item5_2.setText(lines.get(position).getItemName());
            holder.ac_item5_3.setText(CommonUtils.addComma(String.valueOf(lines.get(position).getQuantity())));
            holder.ac_item5_4.setText(String.valueOf(lines.get(position).getPrice()));
            holder.ac_item5_5.setText(String.valueOf(CommonUtils.mul(lines.get(position).getPrice(), lines.get(position).getQuantity())));

            holder.ac_item5_6.setText(lines.get(position).getBatchNumber());

            String ObjectPriceAuth_591 = SPUtils.getString(getApplicationContext(), "ObjectPriceAuth_591");
            final String ObjectAmountAuth_591 = SPUtils.getString(getApplicationContext(), "ObjectAmountAuth_591");
            if (ObjectPriceAuth_591.equals("N")) {
                holder.ac_item5_4.setText("***");
                int i = Color.parseColor("#EBEFF1");
                holder.ac_item5_4.setBackgroundColor(i);
                holder.ac_item5_4.setEnabled(false);
            }
            if (ObjectAmountAuth_591.equals("N")) {
                holder.ac_item5_5.setText("***");
                total_tv6.setText("***");
            }


            holder.ac_item5_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lines.remove(position);
                    notifyDataSetChanged();
                }
            });

            holder.ac_item5_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getInventoryReceiptInfo != null) {
                        final int tag = (int) holder.ac_item5_1.getTag();
                        ListView lv5 = new ListView(InventoryReceiptActivity.this);
                        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                        View view = layoutInflater.inflate(R.layout.item_search, null);
                        lv5.addHeaderView(view);
                        final List<GetInventoryReceiptInfo.Itembean> lists = new ArrayList();
                        List<GetInventoryReceiptInfo.Itembean> item = getInventoryReceiptInfo.getItem();
                        for (GetInventoryReceiptInfo.Itembean itembean : item) {
                            if (itembean.getTransName().equals(tv1.getText().toString()) && itembean.getWhsCode().equals(tv2.getText().toString())) {
                                lists.add(itembean);
                            }
                        }
                        list = lists;
                        final EditText item_search = (EditText) view.findViewById(R.id.item_search);
                        item_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                String trim = item_search.getText().toString().trim();
                                list = new ArrayList<>();
                                for (int i = 0; i < lists.size(); i++) {
                                    if ((lists.get(i).getItemCode() + lists.get(i).getItemName()).contains(trim)) {
                                        GetInventoryReceiptInfo.Itembean itembean = lists.get(i);
                                        list.add(itembean);
                                    }
                                }
                                bmAdapter.notifyDataSetChanged();
                                return false;
                            }
                        });
                        bmAdapter = new BMAdapter();
                        lv5.setAdapter(bmAdapter);
                        lv5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                holder.ac_item5_1.setText(list.get(position - 1).getItemCode());
                                holder.ac_item5_2.setText(list.get(position - 1).getItemName());
                                lines.get(tag).setItemCode(holder.ac_item5_1.getText().toString());
                                lines.get(tag).setItemName(holder.ac_item5_2.getText().toString());
                                lines.get(tag).setIfBatches(list.get(position - 1).getIfBatches());
                            }
                        });
                        PopupWindow popupWindow = null;
                        if (popupWindow == null) {
                            popupWindow = new PopupWindow(lv5, 800, 300);
                            popupWindow.setFocusable(true);
                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                            popupWindow.setOutsideTouchable(true);

                        }
                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        } else {
                            popupWindow.showAsDropDown(holder.ac_item5_1, 0, 0);
                            popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }
                            });
                        }
                    }
                }
            });

            holder.ac_item5_3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = holder.ac_item5_3.getText().toString().trim().replace(",", "");
                    int tag = (int) holder.ac_item5_3.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    double v = Double.parseDouble(trim);
                    double price = lines.get(tag).getPrice();
                    double mul = CommonUtils.mul(v, price);
                    holder.ac_item5_5.setText(String.valueOf(mul));
                    lines.get(tag).setQuantity(v);
                    if (ObjectAmountAuth_591.equals("N")) {
                        holder.ac_item5_5.setText("***");
                        total_tv6.setText("***");
                    }
                    if (!CommonUtils.touzi_ed_values22.equals(holder.ac_item5_3.getText().toString().trim().replace(",", ""))) {
                        holder.ac_item5_3.setText(CommonUtils.addCommas(holder.ac_item5_3.getText().toString().trim().replace(",", ""), holder.ac_item5_3));
                        holder.ac_item5_3.setSelection(CommonUtils.addCommas(holder.ac_item5_3.getText().toString().trim().replace(",", ""), holder.ac_item5_3).length());
                    }
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
                    String trim = holder.ac_item5_4.getText().toString().trim();
                    int tag = (int) holder.ac_item5_4.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "0.0";
                    }
                    if (!trim.equals("***")) {
                        double v = Double.parseDouble(trim);
                        double quantity = lines.get(tag).getQuantity();
                        double mul = CommonUtils.mul(v, quantity);
                        holder.ac_item5_5.setText(String.valueOf(mul));
                        lines.get(tag).setPrice(v);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            holder.ac_item5_6.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String trim = holder.ac_item5_6.getText().toString().trim();
                    int tag = (int) holder.ac_item5_6.getTag();
                    if (trim == null || trim.length() == 0) {
                        trim = "";
                    }
                    lines.get(tag).setBatchNumber(trim);
                }

                @Override
                public void afterTextChanged(Editable s) {
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
            EditText ac_item5_6;
        }
    }

    //事务名称列表的适配器
    private class TransInfoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return transInfo == null ? 0 : transInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return transInfo.get(position);
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
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setText(transInfo.get(position).getTrsCode());
            viewHolder.ac_item2_4.setText(transInfo.get(position).getTrsName());
            return convertView;
        }

        private class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //到仓库列表的适配器
    private class WhsInfoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return whsInfo == null ? 0 : whsInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return whsInfo.get(position);
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
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setText(whsInfo.get(position).getWhsCode());
            viewHolder.ac_item2_4.setText(whsInfo.get(position).getWhsName());
            return convertView;
        }

        private class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //领用部门列表的适配器
    private class UseDepartAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return useDepart == null ? 0 : useDepart.size();
        }

        @Override
        public Object getItem(int position) {
            return useDepart.get(position);
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
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setText(useDepart.get(position).getUseDepartCode());
            viewHolder.ac_item2_4.setText(useDepart.get(position).getUseDepartName());
            return convertView;
        }

        private class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //入库部门列表的适配器
    private class InDepartAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return inDepart == null ? 0 : inDepart.size();
        }

        @Override
        public Object getItem(int position) {
            return inDepart.get(position);
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
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setText(inDepart.get(position).getInDepartCode());
            viewHolder.ac_item2_4.setText(inDepart.get(position).getInDepartName());
            return convertView;
        }

        private class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //成本中心列表的适配器
    private class CostCenterAdapter extends BaseAdapter {
        private final List<GetInventoryReceiptInfo.CostCenterbean> list;

        public CostCenterAdapter(List list) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            list.get(position);
            viewHolder.ac_item2_3.setText(list.get(position).getTrsName());
            viewHolder.ac_item2_4.setText(list.get(position).getCostCenterCode());
            return convertView;
        }

        private class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //源单类型列表的适配器
    private class BaseDocTypeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return baseDocType == null ? 0 : baseDocType.size();
        }

        @Override
        public Object getItem(int position) {
            return baseDocType.get(position);
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
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setText(baseDocType.get(position).getTypeCode());
            viewHolder.ac_item2_4.setText(baseDocType.get(position).getTypeName());
            return convertView;
        }

        private class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

    //编码列表的适配器
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
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(InventoryReceiptActivity.this, R.layout.ac_item2_2, null);
                viewHolder.ac_item2_3 = (TextView) convertView.findViewById(R.id.ac_item2_3);
                viewHolder.ac_item2_4 = (TextView) convertView.findViewById(R.id.ac_item2_4);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_3.setText(list.get(position).getItemCode());
            viewHolder.ac_item2_4.setText(list.get(position).getItemName());
            return convertView;
        }

        private class ViewHolder {
            TextView ac_item2_3;
            TextView ac_item2_4;
        }
    }

}
