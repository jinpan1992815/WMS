package activity;

import android.app.ProgressDialog;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import cn.bosyun.anyou.R;
import pager.GetInformedReportPager;
import pager.GetInventoryTransactionReportPager;
import pager.GetInventoryTransferReportPager;
import pager.GetProductionReceiptReportPager;
import pager.GetPurchaseReturnReportPager;
import pager.GetSalesReturnReportPager;
import pager.GetWarehouseReportPager;

/**
 * Created by Jinpan on 2017/2/24 0024.
 */
public class HistoryListActivity extends BaseActivity {

    private TabPageIndicator tabPageIndicator;
    private ViewPager viewPager;

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_buy;
    }

    @Override
    public void initView(View v) {
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        viewPager = ((ViewPager) findViewById(R.id.pager));
        viewPager.setAdapter(pagerAdapter);
        tabPageIndicator.setViewPager(viewPager);
    }

    String[] titles = new String[]{"到货通知单执行情况", "销售交退货明细", "采购收退货明细", "成品生产入库明细", "非生产性物料出入库明细", "每个仓库的物料进销存报表", "物料转储明细"};
    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return titles == null ? 0 : titles.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ProgressDialog progressDialog = new ProgressDialog(container.getContext(), ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("数据加载中，请稍后……");
            progressDialog.setCancelable(true);
            GetInformedReportPager getInformedReportPager = new GetInformedReportPager(progressDialog, container.getContext());
            GetSalesReturnReportPager getSalesReturnReportPager = new GetSalesReturnReportPager(progressDialog, container.getContext());
            GetPurchaseReturnReportPager getPurchaseReturnReportPager = new GetPurchaseReturnReportPager(progressDialog, container.getContext());
            GetProductionReceiptReportPager getProductionReceiptReportPager = new GetProductionReceiptReportPager(progressDialog, container.getContext());
            GetInventoryTransactionReportPager getInventoryTransactionReportPager = new GetInventoryTransactionReportPager(progressDialog, container.getContext());
            GetWarehouseReportPager getWarehouseReportPager = new GetWarehouseReportPager(progressDialog, container.getContext());
            GetInventoryTransferReportPager getInventoryTransferReportPager = new GetInventoryTransferReportPager(progressDialog, container.getContext());
            if (position == 0) {
                container.addView(getInformedReportPager.getView());
                return getInformedReportPager.getView();
            } else if (position == 1) {
                container.addView(getSalesReturnReportPager.getView());
                return getSalesReturnReportPager.getView();
            } else if (position == 2) {
                container.addView(getPurchaseReturnReportPager.getView());
                return getPurchaseReturnReportPager.getView();
            } else if (position == 3) {
                container.addView(getProductionReceiptReportPager.getView());
                return getProductionReceiptReportPager.getView();
            } else if (position == 4) {
                container.addView(getInventoryTransactionReportPager.getView());
                return getInventoryTransactionReportPager.getView();
            } else if (position == 5) {
                container.addView(getWarehouseReportPager.getView());
                return getWarehouseReportPager.getView();
            } else if (position == 6) {
                container.addView(getInventoryTransferReportPager.getView());
                return getInventoryTransferReportPager.getView();
            }
            return false;
        }

    };
}
