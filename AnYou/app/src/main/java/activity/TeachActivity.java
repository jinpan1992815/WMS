package activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bosyun.anyou.R;

/**
 * Created by Jinpan on 2017/4/1 0001.
 */
public class TeachActivity extends BaseActivity {
    private int[] imgIdArray;
    private ViewPager viewPager;
    private TextView tv;

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_teach;
    }

    @Override
    public void initView(View v) {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tv = ((TextView) findViewById(R.id.tv));
        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.jc01, R.drawable.jc02, R.drawable.jc03, R.drawable.jc04, R.drawable.jc05,
                R.drawable.jc06, R.drawable.jc07, R.drawable.jc08, R.drawable.jc09, R.drawable.jc10, R.drawable.jc11,
                R.drawable.jc12, R.drawable.jc14, R.drawable.jc15, R.drawable.jc16, R.drawable.jc17, R.drawable.jc18,
                R.drawable.jc19, R.drawable.jc20, R.drawable.jc21, R.drawable.jc22, R.drawable.jc23, R.drawable.jc24,
                R.drawable.jc26, R.drawable.jc27, R.drawable.jc28, R.drawable.jc29, R.drawable.jc30, R.drawable.jc31,
                R.drawable.jc32, R.drawable.jc33, R.drawable.jc34, R.drawable.jc35, R.drawable.jc36, R.drawable.jc37,
                R.drawable.jc38, R.drawable.jc39, R.drawable.jc40, R.drawable.jc41, R.drawable.jc42, R.drawable.jc43,
                R.drawable.jc44, R.drawable.jc45, R.drawable.jc46, R.drawable.jc47};
        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv.setText((position + 1) + "/45");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgIdArray == null ? 0 : imgIdArray.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(container.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imgIdArray[position]);

            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
