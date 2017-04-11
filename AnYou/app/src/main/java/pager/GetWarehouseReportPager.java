package pager;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import bean.GetWarehouseReport;
import bean.GetWhsInfo;
import cn.bosyun.anyou.R;
import constant.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.CommonUtils;
import utils.HttpUtils;
import utils.SPUtils;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
public class GetWarehouseReportPager extends Fragment {
    private final Context context;
    private final View rootView;
    private final TextView start_date;
    private final TextView end_date;
    private final EditText keyword;
    private final ListView lv;
    private final TextView ck;
    private final ProgressDialog progressDialog;
    private List<GetWarehouseReport> list;
    private List<GetWhsInfo> list2;
    private String whsName = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                progressDialog.dismiss();
                CommonUtils.showToast(context, "数据加载完毕");
            }
            lv.setAdapter(new MyAdapter());
        }
    };

    public GetWarehouseReportPager(final ProgressDialog progressDialog, final Context context) {
        this.context = context;
        this.progressDialog = progressDialog;

        initCK();
        rootView = View.inflate(context, R.layout.pager_getwarehousereport, null);
        start_date = (TextView) rootView.findViewById(R.id.start_date);
        end_date = (TextView) rootView.findViewById(R.id.end_date);
        ck = ((TextView) rootView.findViewById(R.id.ck));
        keyword = (EditText) rootView.findViewById(R.id.keyword);
        lv = ((ListView) rootView.findViewById(R.id.lv));

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDate(context, start_date);
            }
        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDate(context, end_date);
            }
        });

        ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView lv = new ListView(context);
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.item_blank, null);
                lv.addHeaderView(view);
                lv.setAdapter(new CKAdapter());
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            ck.setText("");
                        } else {
                            ck.setText(list2.get(position - 1).getWhsCode());
                            whsName = list2.get(position - 1).getWhsName();
                        }
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
                    popupWindow.showAsDropDown(ck, 0, 0);
                    popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                }
            }
        });

        keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                progressDialog.show();
                // reJson={"UserName":"hunan","Password":"1234","DBName":"024test","StartDate":"20160101","EndDate":"20161231","WhsName":"","Keyword":""}
                SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String username = sp.getString("username", null);
                String password = sp.getString("password", null);
                String dbname = SPUtils.getString(context, "DBName");
                String StartDate = start_date.getText().toString();
                String EndDate = end_date.getText().toString();
                //String WhsName = ck.getText().toString().trim();
                String Keyword = keyword.getText().toString();
                String json = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"StartDate\":\"" + StartDate + "\",\"EndDate\":\"" + EndDate + "\",\"WhsName\":\"" + whsName + "\",\"Keyword\":\"" + Keyword + "\"}";
                HttpUtils.getResult(Constant.HOST_getWarehouseReport, json, callBack);
                return false;
            }
        });
    }

    private void initCK() {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String username = sp.getString("username", null);
        String password = sp.getString("password", null);
        String dbname = SPUtils.getString(context, "DBName");
        String json = "{\"DBName\":\"" + dbname + "\",\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\"}";
        Callback callBack2 = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CommonUtils.showToast(getActivity(), "请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Type listType = new TypeToken<List<GetWhsInfo>>() {
                }.getType();
                try {
                    list2 = new Gson().fromJson(result, listType);
                } catch (Exception e) {
                    CommonUtils.showToast(getActivity(), "获取数据失败");
                }
            }
        };
        HttpUtils.getResult(Constant.HOST_GetWhsInfo, json, callBack2);
    }

    private void initDate(Context context, final TextView textView) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        textView.setText(DateFormat.format("yyy.MM.dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private Callback callBack = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            CommonUtils.showToast(getActivity(), "请检查网络");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String result = response.body().string();
            Type listType = new TypeToken<List<GetWarehouseReport>>() {
            }.getType();
            try {
                list = new Gson().fromJson(result, listType);
                Message message = Message.obtain();
                message.obj = list;
                handler.sendMessage(message);
            } catch (Exception e) {
                progressDialog.dismiss();
                CommonUtils.showToast(getActivity(), "获取数据失败");
            }
        }
    };

    @Override
    public View getView() {
        return rootView;
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
            GetWarehouseReport getWarehouseReport = list.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.fg_item5_6, null);
                holder.tv1 = ((TextView) convertView.findViewById(R.id.tv1));
                holder.tv2 = ((TextView) convertView.findViewById(R.id.tv2));
                holder.tv3 = ((TextView) convertView.findViewById(R.id.tv3));
                holder.tv4 = ((TextView) convertView.findViewById(R.id.tv4));
                holder.tv5 = ((TextView) convertView.findViewById(R.id.tv5));
                holder.tv6 = ((TextView) convertView.findViewById(R.id.tv6));
                holder.tv7 = ((TextView) convertView.findViewById(R.id.tv7));
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }
            holder.tv1.setText(getWarehouseReport.getWhsName());
            holder.tv2.setText(getWarehouseReport.getItemCode());
            holder.tv3.setText(getWarehouseReport.getDscription());
            holder.tv4.setText(CommonUtils.addComma(String.valueOf(getWarehouseReport.getInitialQty())));
            holder.tv5.setText(CommonUtils.addComma(String.valueOf(getWarehouseReport.getInQty())));
            holder.tv6.setText(CommonUtils.addComma(String.valueOf(getWarehouseReport.getOutQty())));
            holder.tv7.setText(CommonUtils.addComma(String.valueOf(getWarehouseReport.getFinalQty())));
            return convertView;
        }

        private class ViewHolder {
            TextView tv1;
            TextView tv2;
            TextView tv3;
            TextView tv4;
            TextView tv5;
            TextView tv6;
            TextView tv7;
        }
    }

    //仓库列表的适配器
    private class CKAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list2 == null ? 0 : list2.size();
        }

        @Override
        public Object getItem(int position) {
            return list2.get(position);
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
                convertView = View.inflate(context, R.layout.ac_item1_2, null);
                viewHolder.ac_item2_1 = (TextView) convertView.findViewById(R.id.ac_item2_1);
                viewHolder.ac_item2_2 = (TextView) convertView.findViewById(R.id.ac_item2_2);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = ((ViewHolder) convertView.getTag());
            }
            viewHolder.ac_item2_1.setTag(position);
            viewHolder.ac_item2_2.setTag(position);

            viewHolder.ac_item2_1.setText(list2.get(position).getWhsCode());
            viewHolder.ac_item2_2.setText(list2.get(position).getWhsName());
            return convertView;
        }

        class ViewHolder {

            TextView ac_item2_1;
            TextView ac_item2_2;
        }
    }
}
