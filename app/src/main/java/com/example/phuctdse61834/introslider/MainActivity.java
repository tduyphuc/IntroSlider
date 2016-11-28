package com.example.phuctdse61834.introslider;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int DOTS_HEIGHT = 45;
    private Button btnSkip, btnNext;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView dots[];
    private int[] layoutsArr;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_slider_activity);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout)findViewById(R.id.dots);
        btnNext = (Button)findViewById(R.id.button_next);
        btnSkip = (Button)findViewById(R.id.button_skip);

        layoutsArr = new int[]{
                R.layout.slide_1,
                R.layout.slide_2,
                R.layout.slide_3,
                R.layout.slide_4};

        intiDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(vpListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem();
                if(current < layoutsArr.length){
                    viewPager.setCurrentItem(current + 1);
                }
            }
        });
    }

    private int getItem() {
        return viewPager.getCurrentItem();
    }

    ViewPager.OnPageChangeListener vpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            intiDots(position);
            if(position == layoutsArr.length - 1){
                btnNext.setText(getString(R.string.name_btn_start));
                btnSkip.setVisibility(View.INVISIBLE);
            }
            else{
                btnNext.setText(getString(R.string.name_btn_next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void intiDots(int currentPage){
        if(dots == null){
            dots = new TextView[layoutsArr.length];
        }

        int[] color_inactive = getResources().getIntArray(R.array.dot_inactive_array);
        int[] color_active = getResources().getIntArray(R.array.dot_active_array);

        dotsLayout.removeAllViews();
        for(int i = 0; i < layoutsArr.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(DOTS_HEIGHT);
            dots[i].setTextColor(color_inactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        dots[currentPage].setTextColor(color_active[currentPage]);
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter(){
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layoutsArr[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return layoutsArr.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}
