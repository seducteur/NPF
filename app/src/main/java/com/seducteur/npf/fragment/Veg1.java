package com.seducteur.npf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seducteur.npf.activity.EventListActivity;
import com.seducteur.npf.R;
import com.seducteur.npf.activity.ScheduleActivity;


/**
 * Created by x-note on 2016-03-04.
 */
public class Veg1 extends Fragment /*implements View.OnClickListener*/ {

    private Button mEventBtn;
    private Button mScheduleBtn;
    private LinearLayout mFrag1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null) {
            return null;
        }

        mFrag1 = (LinearLayout) inflater.inflate(R.layout.veg1_layout, container, false);
        //initialize();

        return mFrag1;
    }

    private void initialize() {
        initializeFields();
        initializeListeners();
        initializeView();
    }

    private void initializeFields() {
        /*mEventBtn = (Button) mFrag1.findViewById(R.id.eventBtn);
        mScheduleBtn = (Button) mFrag1.findViewById(R.id.scheduleBtn);*/
    }

    private void initializeListeners() {
       /* mEventBtn.setOnClickListener(this);
        mScheduleBtn.setOnClickListener(this);*/
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eventBtn :
                Log.d("Test", "BtnClick");
                Toast.makeText(getActivity(), "@@@@TEST@@@@@@", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), EventListActivity.class);
                startActivity(intent);
                break;
            case R.id.scheduleBtn :
                Intent i = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(i);
        }
    }*/

    private void initializeView() {

       /* mEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "BtnClick");
                Toast.makeText(getActivity(), "@@@@TEST@@@@@@", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), EventListActivity.class);
                startActivity(intent);
            }
        });


        mScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(i);
            }
        });*/
    }

   /* @Override //프래그먼트를 포함하고 있는 액티비티의 생성이 완료되었을 때, 즉 액티비티의 onCreate() 메서드가 끝났을 때 호출
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView(); // 만들어진 뷰를 가져옴
        mEventBtn = (Button) view.findViewById(R.id.eventBtn);

        mEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "BtnClick");
                Toast.makeText(getActivity(), "@@@@TEST@@@@@@", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), EventListActivity.class);
                startActivity(intent);
            }
        });
    }*/
}
