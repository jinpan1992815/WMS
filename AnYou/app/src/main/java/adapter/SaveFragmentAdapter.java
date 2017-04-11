package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import activity.SaveActivity2;
import bean.GetOpenInvTransRequestList;
import cn.bosyun.anyou.R;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/6 0006.
 */
public class SaveFragmentAdapter extends BaseAdapter {

    Context context;
    private List<GetOpenInvTransRequestList> list;

    public SaveFragmentAdapter(Context context, List<GetOpenInvTransRequestList> list) {
        this.context = context;
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
        final ViewHolder holder;
        final GetOpenInvTransRequestList getOpenInvTransRequestList = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.fg_item4, null);
            holder.save_item1 = ((TextView) convertView.findViewById(R.id.save_item1));
            holder.save_item2 = ((TextView) convertView.findViewById(R.id.save_item2));
            holder.save_item3 = ((TextView) convertView.findViewById(R.id.save_item3));
            holder.save_item4 = ((TextView) convertView.findViewById(R.id.save_item4));
            holder.save_item5 = (Button) convertView.findViewById(R.id.save_item5);
            holder.save_item5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String objectAuth_67 = SPUtils.getString(context, "ObjectAuth_67");
                    if (objectAuth_67.equals("F") || objectAuth_67.equals("R")) {
                        int position = (int) holder.save_item5.getTag();
                        int docEntry = list.get(position).getDocEntry();
                        Intent intent = new Intent(context, SaveActivity2.class);
                        intent.putExtra("docEntry", String.valueOf(docEntry));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (objectAuth_67.equals("N")) {
                        CommonUtils.showToast(context, "没有权限");
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }

        holder.save_item1.setTag(position);
        holder.save_item2.setTag(position);
        holder.save_item3.setTag(position);
        holder.save_item4.setTag(position);
        holder.save_item5.setTag(position);

        holder.save_item1.setText(getOpenInvTransRequestList.getDocDate());
        holder.save_item2.setText(getOpenInvTransRequestList.getDocNum());
        holder.save_item3.setText(getOpenInvTransRequestList.getFiller());
        holder.save_item4.setText(CommonUtils.addComma(String.valueOf(getOpenInvTransRequestList.getQuantity())));
        holder.save_item5.setText("复制到");
        return convertView;
    }

    static class ViewHolder {

        TextView save_item1;
        TextView save_item2;
        TextView save_item3;
        TextView save_item4;
        Button save_item5;
    }
}
