package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import activity.SellActivity;
import bean.GetOpenPOList;
import cn.bosyun.anyou.R;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/6 0006.
 */
public class SellFragmentAdapter extends BaseAdapter {

    Context context;
    private List<GetOpenPOList> list;

    public SellFragmentAdapter(Context context, List<GetOpenPOList> list) {
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
        final GetOpenPOList getOpenPOList = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.fg_item2, null);
            holder.sell_item1 = ((TextView) convertView.findViewById(R.id.sell_item1));
            holder.sell_item2 = ((TextView) convertView.findViewById(R.id.sell_item2));
            holder.sell_item3 = ((TextView) convertView.findViewById(R.id.sell_item3));
            holder.sell_item4 = ((TextView) convertView.findViewById(R.id.sell_item4));
            holder.sell_item5 = (Button) convertView.findViewById(R.id.sell_item5);
            holder.sell_item5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String objectAuth_15 = SPUtils.getString(context, "ObjectAuth_15");
                    if (objectAuth_15.equals("F") || objectAuth_15.equals("R")) {
                        int position = (int) holder.sell_item5.getTag();
                        int docEntry = list.get(position).getDocEntry();
                        Intent intent = new Intent(context, SellActivity.class);
                        intent.putExtra("docEntry", String.valueOf(docEntry));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (objectAuth_15.equals("N")) {
                        CommonUtils.showToast(context, "没有权限");
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }

        holder.sell_item1.setTag(position);
        holder.sell_item2.setTag(position);
        holder.sell_item3.setTag(position);
        holder.sell_item4.setTag(position);
        holder.sell_item5.setTag(position);

        holder.sell_item1.setText(getOpenPOList.getDocDate());
        holder.sell_item2.setText(getOpenPOList.getDocNum());
        holder.sell_item3.setText(getOpenPOList.getCardName());
        holder.sell_item4.setText(CommonUtils.addComma(getOpenPOList.getOpenQuanity()));
        holder.sell_item5.setText("复制到");
        return convertView;
    }

    static class ViewHolder {

        TextView sell_item1;
        TextView sell_item2;
        TextView sell_item3;
        TextView sell_item4;
        Button sell_item5;
    }
}
