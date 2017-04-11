package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import activity.BuyActivity;
import bean.GetOpenPOList;
import cn.bosyun.anyou.R;
import constant.Constant;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/6 0006.
 */
public class GetOpenPOListPagerAdapter extends BaseAdapter {
    private List<GetOpenPOList> list;
    private Context context;

    public GetOpenPOListPagerAdapter(Context context, List<GetOpenPOList> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final GetOpenPOList getOpenPOList = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.fg_item1_1, null);
            holder.pager1_item1 = ((TextView) convertView.findViewById(R.id.pager1_item1));
            holder.pager1_item2 = ((TextView) convertView.findViewById(R.id.pager1_item2));
            holder.pager1_item3 = ((TextView) convertView.findViewById(R.id.pager1_item3));
            holder.pager1_item4 = ((TextView) convertView.findViewById(R.id.pager1_item4));
            holder.pager1_item5 = (Button) convertView.findViewById(R.id.pager1_item5);
            holder.pager1_item5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String objectAuth_22 = SPUtils.getString(context, "ObjectAuth_22");
                    if (objectAuth_22.equals("F") || objectAuth_22.equals("R")) {
                        int position = (int) holder.pager1_item5.getTag();
                        int docEntry = list.get(position).getDocEntry();
                        Intent intent = new Intent(context, BuyActivity.class);
                        intent.putExtra("docEntry", String.valueOf(docEntry));
                        intent.putExtra("url", Constant.HOST_GetOpenPODetail);
                        intent.putExtra("urlAdd", Constant.HOST_AddGRPOBasedonPO);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else if (objectAuth_22.equals("N")) {
                        CommonUtils.showToast(context, "没有权限");
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) convertView.getTag());
        }

        holder.pager1_item1.setTag(position);
        holder.pager1_item2.setTag(position);
        holder.pager1_item3.setTag(position);
        holder.pager1_item4.setTag(position);
        holder.pager1_item5.setTag(position);

        holder.pager1_item1.setText(getOpenPOList.getDocDate());
        holder.pager1_item2.setText(getOpenPOList.getDocNum());
        holder.pager1_item3.setText(getOpenPOList.getCardName());
        holder.pager1_item4.setText(CommonUtils.addComma(getOpenPOList.getOpenQuanity()));
        holder.pager1_item5.setText("复制到");
        return convertView;
    }

    static class ViewHolder {

        TextView pager1_item1;
        TextView pager1_item2;
        TextView pager1_item3;
        TextView pager1_item4;
        Button pager1_item5;
    }
}
