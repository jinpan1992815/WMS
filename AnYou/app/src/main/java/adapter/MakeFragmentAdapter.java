package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import activity.MakeActivity;
import bean.GetOpenProducitonOrderList;
import cn.bosyun.anyou.R;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/6 0006.
 */
public class MakeFragmentAdapter extends BaseAdapter {

    Context context;
    private List<GetOpenProducitonOrderList> list;

    public MakeFragmentAdapter(Context context, List<GetOpenProducitonOrderList> list) {
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
        final GetOpenProducitonOrderList getOpenProducitonOrderList = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.fg_item3, null);
            holder.make_item1 = ((TextView) convertView.findViewById(R.id.make_item1));
            holder.make_item2 = ((TextView) convertView.findViewById(R.id.make_item2));
            holder.make_item3 = ((TextView) convertView.findViewById(R.id.make_item3));
            holder.make_item4 = ((TextView) convertView.findViewById(R.id.make_item4));
            holder.make_item5 = ((TextView) convertView.findViewById(R.id.make_item5));
            holder.make_item6 = (Button) convertView.findViewById(R.id.make_item6);
            holder.make_item6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String objectAuth_590 = SPUtils.getString(context, "ObjectAuth_590");
                    if (objectAuth_590.equals("F") || objectAuth_590.equals("R")) {
                        int position = (int) holder.make_item6.getTag();
                        int docEntry = list.get(position).getDocEntry();
                        Intent intent = new Intent(context, MakeActivity.class);
                        intent.putExtra("docEntry", String.valueOf(docEntry));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (objectAuth_590.equals("N")) {
                        CommonUtils.showToast(context, "没有权限");
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }

        holder.make_item1.setTag(position);
        holder.make_item2.setTag(position);
        holder.make_item3.setTag(position);
        holder.make_item4.setTag(position);
        holder.make_item5.setTag(position);
        holder.make_item6.setTag(position);

        holder.make_item1.setText(getOpenProducitonOrderList.getDocDate());
        holder.make_item2.setText(getOpenProducitonOrderList.getDocNum());
        holder.make_item3.setText(getOpenProducitonOrderList.getItemName());
        holder.make_item4.setText(CommonUtils.addComma(String.valueOf(getOpenProducitonOrderList.getPlannedQty())));
        holder.make_item5.setText(CommonUtils.addComma(String.valueOf(getOpenProducitonOrderList.getOpenQty())));
        holder.make_item6.setText("复制到");
        return convertView;
    }

    static class ViewHolder {

        TextView make_item1;
        TextView make_item2;
        TextView make_item3;
        TextView make_item4;
        TextView make_item5;
        Button make_item6;
    }
}
