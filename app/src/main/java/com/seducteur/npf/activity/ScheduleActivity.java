package com.seducteur.npf.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.seducteur.npf.adapter.PagerAdapterVeg;
import com.seducteur.npf.fragment.Tab4;
import com.seducteur.npf.fragment.Tab5;
import com.seducteur.npf.fragment.Tab6;
import com.seducteur.npf.R;

import java.util.List;
import java.util.Vector;

public class ScheduleActivity extends AppCompatActivity {

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        initialize();

    }

    private void initialize() {
        initializeFields();
        initializeFragments();
        initializeListeners();
        initializeView();
    }

    private void initializeFields() {
        //Fragments
        mFragments = new Vector<>();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initializeListeners() {

    }

    private void initializeFragments() {
        mFragments.add(Fragment.instantiate(this, Tab4.class.getName()));
        mFragments.add(Fragment.instantiate(this, Tab5.class.getName()));
        mFragments.add(Fragment.instantiate(this, Tab6.class.getName()));

        mPagerAdapter = new PagerAdapterVeg(this.getSupportFragmentManager(), mFragments); //pageAdapterVeg에 파라미터 넘기고(SupportFragmentManger()이랑, fragments에 추가한 class)
    }
    private void initializeView() {

        mViewPager.setAdapter(mPagerAdapter); //ViewPage에 fragment 추가 시키고

        //ActionBar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Schedule");

        //tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction f) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
        //tab추가
        actionBar.addTab(actionBar.newTab().setText("Event").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Exhibition").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Presenters").setTabListener(tabListener));

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
    }

  /*  private void initializeDataSetting() {
        SharedPreferences pref = getSharedPreferences("Pref1", 0);
        TextView tx = (TextView) findViewById(R.id.txt);
        //0 해제 1 추가
        if(pref.getInt("using", 0) == 0) {
            tx.setText(pref.getString("TITLEDATA", ""));
            Log.d("즐겨찾기해제", ":" + tx.toString());
        } else {
            tx.setText(pref.getString("TITLEDATA",""));
            Log.d("즐겨찾기", ":" + tx.toString());
        }
    }*/

}

