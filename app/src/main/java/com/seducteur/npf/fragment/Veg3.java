package com.seducteur.npf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seducteur.npf.R;

/**
 * Created by x-note on 2016-03-04.
 */
public class Veg3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null) {
            return null;
        }
        return (LinearLayout) inflater.inflate(R.layout.veg3_layout, container, false);
    }

}
