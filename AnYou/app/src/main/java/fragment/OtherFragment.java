package fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import activity.BuyReturnActivity1;
import activity.HistoryListActivity;
import activity.InventoryIssueActivity;
import activity.InventoryReceiptActivity;
import activity.SellReturnActivity1;
import cn.bosyun.anyou.R;
import utils.CommonUtils;
import utils.SPUtils;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class OtherFragment extends BaseFragment {
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;

    @Override
    protected void initView(TextView tv_title, View childView) {
        tv_title.setText("其他操作");
        tv_title.setVisibility(View.VISIBLE);

        iv1 = ((ImageView) childView.findViewById(R.id.iv1));
        iv2 = ((ImageView) childView.findViewById(R.id.iv2));
        iv3 = ((ImageView) childView.findViewById(R.id.iv3));
        iv4 = ((ImageView) childView.findViewById(R.id.iv4));
        iv5 = ((ImageView) childView.findViewById(R.id.iv5));
        iv6 = ((ImageView) childView.findViewById(R.id.iv6));

        iv1.setOnClickListener(onClick);
        iv2.setOnClickListener(onClick);
        iv3.setOnClickListener(onClick);
        iv4.setOnClickListener(onClick);
        iv5.setOnClickListener(onClick);
        iv6.setOnClickListener(onClick);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //采购退货按钮的监听
                case R.id.iv1:
                    String objectAuth_21 = SPUtils.getString(getActivity(), "ObjectAuth_21");
                    if (objectAuth_21.equals("F") || objectAuth_21.equals("R")) {
                        CommonUtils.toActivity(getActivity(), BuyReturnActivity1.class);
                    } else if (objectAuth_21.equals("N")) {
                        CommonUtils.showToast(getActivity(), "没有权限");
                    }
                    break;

                //销售退货按钮的监听
                case R.id.iv2:
                    String ObjectAuth_16 = SPUtils.getString(getActivity(), "ObjectAuth_16");
                    if (ObjectAuth_16.equals("F") || ObjectAuth_16.equals("R")) {
                        CommonUtils.toActivity(getActivity(), SellReturnActivity1.class);
                    } else if (ObjectAuth_16.equals("N")) {
                        CommonUtils.showToast(getActivity(), "没有权限");
                    }
                    break;

                //历史单据查询按钮的监听
                case R.id.iv3:
                    CommonUtils.toActivity(getActivity(), HistoryListActivity.class);
                    break;

                //库存收货按钮的监听
                case R.id.iv4:
                    String objectAuth_591 = SPUtils.getString(getActivity(), "ObjectAuth_591");
                    if (objectAuth_591.equals("F")) {
                        CommonUtils.toActivity(getActivity(), InventoryReceiptActivity.class);
                    } else if (objectAuth_591.equals("N") || objectAuth_591.equals("R")) {
                        CommonUtils.showToast(getActivity(), "没有权限");
                    }
                    break;

                //库存发货按钮的监听
                case R.id.iv5:
                    String objectAuth_601 = SPUtils.getString(getActivity(), "ObjectAuth_601");
                    if (objectAuth_601.equals("F")) {
                        CommonUtils.toActivity(getActivity(), InventoryIssueActivity.class);
                    } else if (objectAuth_601.equals("N") || objectAuth_601.equals("R")) {
                        CommonUtils.showToast(getActivity(), "没有权限");
                    }
                    break;

                //库存发货按钮的监听
                case R.id.iv6:
                    CommonUtils.showToast(getActivity(), "设置功能待开发");
                    break;
            }
        }
    };

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initData() {

    }
}
