package com.seducteur.npf.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//모델class => Data 담을만한 클래스  MVC패턴을 예로 M(model(data))
public class TravelInfoModel {

    private int mImageRes; //List Image
    private String mArea;

    public TravelInfoModel(int imageRes, String area) {
        this.mImageRes = imageRes;
        this.mArea = area;
    }

    public int getmImageRes() {
        return mImageRes;
    }
    public  void setmImageRes(int imageRes) {
        this.mImageRes = imageRes;
    }

    public String getmArea() {
        return mArea;
    }
    public void setmArea(String area) {
        this.mArea = area;
    }


}
