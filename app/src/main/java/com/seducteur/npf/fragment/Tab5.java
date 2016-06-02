package com.seducteur.npf.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.seducteur.npf.activity.EventDetailActivity;
import com.seducteur.npf.R;

import java.util.ArrayList;

/**
 * Created by x-note on 2016-03-04.
 */
public class Tab5 extends Fragment {
    private String[] mData = {"Exhibition1", "Exhibition2","Exhibition3"};
    private ArrayAdapter<String> mAdapter;
    private ListView mListView;
    private LinearLayout mFrag1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null) {
            return null;
        }
        mFrag1 = (LinearLayout) inflater.inflate(R.layout.tab5_layout, container, false);
        initialize();
        return mFrag1;
    }

    private void initialize() {
        initializeFields();
        initializeView();
    }

    private void initializeFields() {
        mListView = (ListView) mFrag1.findViewById(R.id.list_view);
    }

    private void initializeView() {

        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mData);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), parent.getItemIdAtPosition(position) + "selected", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                startActivity(intent);
            }
        });

    }

}
