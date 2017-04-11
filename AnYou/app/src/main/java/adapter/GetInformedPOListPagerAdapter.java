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
import bean.GetInformedPOList;
import cn.bosyun.anyou.R;
import constant.Constant;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Jinpan on 2016/12/6 0006.
 */
public class GetInformedPOListPagerAdapter extends BaseAdapter {
    private List<GetInformedPOList> list;
    private Context context;

    public GetInformedPOListPagerAdapter(Context context, List<GetInformedPOList> list) {
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
        final GetInformedPOList getInformedPOList = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.fg_item1_2, null);
            holder.pager2_item1 = ((TextView) convertView.findViewById(R.id.pager2_item1));
            holder.pager2_item2 = ((TextView) convertView.findViewById(R.id.pager2_item2));
            holder.pager2_item3 = ((TextView) convertView.findViewById(R.id.pager2_item3));
            holder.pager2_item4 = ((TextView) convertView.findViewById(R.id.pager2_item4));
            holder.pager2_item5 = ((TextView) convertView.findViewById(R.id.pager2_item5));
            holder.pager2_item6 = ((TextView) convertView.findViewById(R.id.pager2_item6));
            holder.pager2_item7 = (Button) convertView.findViewById(R.id.pager2_item7);
            holder.pager2_item7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String objectAuth_22 = SPUtils.getString(context, "ObjectAuth_22");
                    if (objectAuth_22.equals("F") || objectAuth_22.equals("R")) {
                        int position = (int) holder.pager2_item7.getTag();
                        int docEntry = list.get(position).getDocEntry();
                        Intent intent = new Intent(context, BuyActivity.class);
                        intent.putExtra("docEntry", String.valueOf(docEntry));
                        intent.putExtra("url", Constant.HOST_GetInformedPODetail);
                        intent.putExtra("urlAdd", Constant.HOST_AddGRPOBasedonInformedPO);
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

        holder.pager2_item1.setTag(position);
        holder.pager2_item2.setTag(position);
        holder.pager2_item3.setTag(position);
        holder.pager2_item4.setTag(position);
        holder.pager2_item5.setTag(position);
        holder.pager2_item6.setTag(position);
        holder.pager2_item7.setTag(position);

        holder.pager2_item1.setText(getInformedPOList.getDocDate());
        holder.pager2_item2.setText(getInformedPOList.getDocNum());
        holder.pager2_item3.setText(getInformedPOList.getCardName());
        holder.pager2_item4.setText(getInformedPOList.getCarNumber());
        holder.pager2_item5.setText(CommonUtils.addComma(getInformedPOList.getOrderAmount()));
        holder.pager2_item6.setText(CommonUtils.addComma(getInformedPOList.getPlanQuantity()));
        holder.pager2_item7.setText("复制到");
        return convertView;
    }

    static class ViewHolder {

        TextView pager2_item1;
        TextView pager2_item2;
        TextView pager2_item3;
        TextView pager2_item4;
        TextView pager2_item5;
        TextView pager2_item6;
        Button pager2_item7;
    }
}
