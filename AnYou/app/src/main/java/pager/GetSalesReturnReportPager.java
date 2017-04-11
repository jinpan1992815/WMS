package pager;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import bean.GetSalesReturnReport;
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
public class GetSalesReturnReportPager extends Fragment {

    private final Context context;
    private final View rootView;
    private final TextView start_date;
    private final TextView end_date;
    private final EditText keyword;
    private final ListView lv;
    private final ProgressDialog progressDialog;
    private List<GetSalesReturnReport> list;

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


    public GetSalesReturnReportPager(final ProgressDialog progressDialog, final Context context) {
        this.context = context;
        this.progressDialog = progressDialog;

        rootView = View.inflate(context, R.layout.pager_getsalesreturnreport, null);
        start_date = (TextView) rootView.findViewById(R.id.start_date);
        end_date = (TextView) rootView.findViewById(R.id.end_date);
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

        keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                progressDialog.show();
                // reJson={"UserName":"hunan","Password":"1234","DBName":"024test","StartDate":"20160101","EndDate":"20161231","Keyword":"武汉"}
                SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String username = sp.getString("username", null);
                String password = sp.getString("password", null);
                String dbname = SPUtils.getString(context, "DBName");
                String StartDate = start_date.getText().toString();
                String EndDate = end_date.getText().toString();
                String Keyword = keyword.getText().toString();
                String json = "{\"UserName\":\"" + username + "\",\"Password\":\"" + password + "\",\"DBName\":\"" + dbname + "\",\"StartDate\":\"" + StartDate + "\",\"EndDate\":\"" + EndDate + "\",\"Keyword\":\"" + Keyword + "\"}";
                HttpUtils.getResult(Constant.HOST_getSalesReturnReport, json, callBack);
                return false;
            }
        });
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
            Type listType = new TypeToken<List<GetSalesReturnReport>>() {
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
            GetSalesReturnReport getSalesReturnReport = list.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.fg_item5_2, null);
                holder.tv1 = ((TextView) convertView.findViewById(R.id.tv1));
                holder.tv2 = ((TextView) convertView.findViewById(R.id.tv2));
                holder.tv3 = ((TextView) convertView.findViewById(R.id.tv3));
                holder.tv4 = ((TextView) convertView.findViewById(R.id.tv4));
                holder.tv5 = ((TextView) convertView.findViewById(R.id.tv5));
                holder.tv6 = ((TextView) convertView.findViewById(R.id.tv6));
                holder.tv7 = ((TextView) convertView.findViewById(R.id.tv7));
                holder.tv8 = ((TextView) convertView.findViewById(R.id.tv8));
                holder.tv9 = ((TextView) convertView.findViewById(R.id.tv9));
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }
            holder.tv1.setText(getSalesReturnReport.getDocType());
            holder.tv2.setText(getSalesReturnReport.getCardName());
            holder.tv3.setText(getSalesReturnReport.getDocDate());
            holder.tv4.setText(getSalesReturnReport.getDocEntry());
            holder.tv5.setText(getSalesReturnReport.getDocEntry2());
            holder.tv6.setText(getSalesReturnReport.getDscription());
            holder.tv7.setText(String.valueOf(getSalesReturnReport.getBagAmount()));
            holder.tv8.setText(CommonUtils.addComma(String.valueOf(getSalesReturnReport.getWeight())));
            holder.tv9.setText(getSalesReturnReport.getWhsName());
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
            TextView tv8;
            TextView tv9;
        }
    }

}
