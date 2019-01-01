package com.example.practicetest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.practicetest.R;

public class LogActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] strings = new String[]{"商品","详情","评论"};
            @Override
            public Fragment getItem(int i) {
                switch (i){

                }
                return null;
            }

            @Override
            public int getCount() {
                return strings.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return strings[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }
    }

