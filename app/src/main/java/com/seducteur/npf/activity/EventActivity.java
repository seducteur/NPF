package com.seducteur.npf.activity;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.seducteur.npf.adapter.PagerAdapterVeg;
import com.seducteur.npf.fragment.Tab1;
import com.seducteur.npf.fragment.Tab2;
import com.seducteur.npf.fragment.Tab3;
import com.seducteur.npf.R;

import java.util.List;
import java.util.Vector;

public class EventActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabview_layout);

                initialize();

    }
    private void initialize() {
        initializeFields();
        initializeListeners();
        initializeFragments();
        initializeView();
    }

    private void initializeFields() {
        mFragments = new Vector<>();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initializeListeners() {

    }

    private void initializeFragments() {
        //Fragments
        mFragments.add(Fragment.instantiate(this, Tab1.class.getName()));
        mFragments.add(Fragment.instantiate(this, Tab2.class.getName()));
        mFragments.add(Fragment.instantiate(this, Tab3.class.getName()));

        mPagerAdapter = new PagerAdapterVeg(this.getSupportFragmentManager(), mFragments); //pageAdapterVeg에 파라미터 넘기고(SupportFragmentManger()이랑, fragments에 추가한 class)
    }

    private void initializeView() {

        mViewPager.setAdapter(mPagerAdapter); //ViewPage에 fragment 추가

        /** ActionBar Setting **/
        final ActionBar actionBar = getSupportActionBar();

        /*getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));//actionbar 색상*/
        //Custom Actionbar 사용하기 위해 CustomEnabled를 true 시키고 필요없는 것들 체크
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false); //홈아이콘을 클릭햇을 때 어떤 처리를 하고싶다면 true
        actionBar.setDisplayShowTitleEnabled(false); //타이틀을 보여줄지 말지

        // Custom View Layout Set
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.main_actionbar_custom, null);
        actionBar.setCustomView(mCustomView, new ActionBar.LayoutParams(Gravity.CENTER)); //ActionBar Set & 레이아웃 가운데 정렬(레이아웃 ActionBar.LayoutParams.WRAP_CONTENT 식으로 레이아웃 수정할 수 있음)
        // ActionBar Color
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        //tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction f) {
                mViewPager.setCurrentItem(tab.getPosition()); //탭 클릭했을때 뷰페이지 변경
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        actionBar.addTab(actionBar.newTab().setText("추천명소").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("맛집").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("축제").setTabListener(tabListener));

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
           public void onPageSelected(int position) {
               actionBar.setSelectedNavigationItem(position); //뷰페이지 넘겼을때 탭 변경(2번째 뷰면 2번재 탭으로)
            }
        });
    }

}

