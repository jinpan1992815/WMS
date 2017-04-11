package fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;

import cn.bosyun.anyou.R;
import pager.GetInformedPOListPager;
import pager.GetOpenPOListPager;
import utils.SPUtils;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class BuyFragment extends BaseFragment {
    private ViewPager viewPager;
    private TabPageIndicator tabPageIndicator;


    @Override
    protected void initView(TextView tv_title, View childView) {
        tv_title.setText("采购入库");
        tv_title.setVisibility(View.VISIBLE);
        viewPager = ((ViewPager) childView.findViewById(R.id.pager));
        tabPageIndicator = ((TabPageIndicator) childView.findViewById(R.id.indicator));
        viewPager.setAdapter(pagerAdapter);
        tabPageIndicator.setViewPager(viewPager);

    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            viewPager.setAdapter(pagerAdapter);
            tabPageIndicator.setViewPager(viewPager);
        }
    }

    String[] titles = new String[]{"未清采购订单", "到货通知单"};
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
            GetOpenPOListPager getOpenPOListPager = new GetOpenPOListPager(container.getContext());
            GetInformedPOListPager getInformedPOListPager = new GetInformedPOListPager(container.getContext());
            if (position == 0) {
                container.addView(getOpenPOListPager.getView());
                return getOpenPOListPager.getView();
            } else if (position == 1) {
                container.addView(getInformedPOListPager.getView());
                return getInformedPOListPager.getView();
            }
            return false;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        SPUtils.delete(getActivity(), "sm1");
        SPUtils.delete(getActivity(), "sm2");
    }
}


