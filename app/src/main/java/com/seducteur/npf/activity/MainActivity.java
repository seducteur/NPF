package com.seducteur.npf.activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seducteur.npf.adapter.PagerAdapterVeg;
import com.seducteur.npf.fragment.Veg1;
import com.seducteur.npf.fragment.Veg2;
import com.seducteur.npf.fragment.Veg3;
import com.seducteur.npf.R;

import me.relex.circleindicator.CircleIndicator;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private CircleIndicator mCircleIndicator;
    private List<Fragment> mFragments;
    private Button mTravelBtn, mFavBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vgeview_layout);
        initialize();
    }

    private void initialize() {
        initializeFields();
        initializeListeners();
        initializeFragments();
        initializeView();
    }

    private void  initializeFields() {
        mFragments = new Vector<Fragment>();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCircleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        mTravelBtn = (Button) findViewById(R.id.travelBtn);
        mFavBtn = (Button) findViewById(R.id.favBtn);
    }

    private void initializeListeners() {
        mTravelBtn.setOnClickListener(this);
        mFavBtn.setOnClickListener(this);
    }

    private void initializeFragments() {
        //fargment 추가
        mFragments.add(Fragment.instantiate(this, Veg1.class.getName()));
        mFragments.add(Fragment.instantiate(this, Veg2.class.getName()));
        mFragments.add(Fragment.instantiate(this, Veg3.class.getName()));

        mPagerAdapter = new PagerAdapterVeg(this.getSupportFragmentManager(), mFragments); //pageAdapterVeg에 파라미터 넘기고(SupportFragmentManger()이랑, fragments에 추가한 class)
    }

    private void initializeView() {
        ActionBar actionBar = getSupportActionBar();

        mViewPager.setAdapter(mPagerAdapter); //ViewPage에 fragment 추가 시키고

        //indicator viewpager에 연결
        mCircleIndicator.setViewPager(mViewPager);

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.travelBtn :
                Log.d("Test", "BtnClick");
                Toast.makeText(this, "@@@@TEST@@@@@@", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, EventListActivity.class);
                startActivity(intent);
                break;
            case R.id.favBtn :
                Intent i = new Intent(this, ScheduleActivity.class);
                startActivity(i);
        }
    }


}
