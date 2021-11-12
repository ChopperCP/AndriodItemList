package com.example.tab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.main.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class BookListTabActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPage;
    private String[] tabTitles;//tab的标题
    private List<Fragment> mDatas = new ArrayList<>();//ViewPage2的Fragment容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        initData();
        //找到控件
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPage = findViewById(R.id.view_page);
        //创建适配器
        BookListTabAdapter mAdapter = new BookListTabAdapter(this, mDatas);
        mViewPage.setAdapter(mAdapter);

        //TabLayout与ViewPage2联动关键代码
        new TabLayoutMediator(mTabLayout, mViewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();

        //ViewPage2选中改变监听
        mViewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        //TabLayout的选中改变监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

        //初始化数据
        private void initData(){
            tabTitles = new String[]{"导航一", "导航二"};
            FragmentMain frgMain = new FragmentMain();
            FragmentWebView frgWebView = new FragmentWebView();
            mDatas.add(frgMain);
            mDatas.add(frgWebView);
        }
}
