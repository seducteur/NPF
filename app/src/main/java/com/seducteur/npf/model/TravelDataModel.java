package com.seducteur.npf.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

//모델class => Data 담을만한 클래스  MVC패턴을 예로 M(model(data))
public class TravelDataModel implements Parcelable{
    //drwarble에 img는 R파일에 integer로 자동으로 생성되어 있어서 int로 선언
    //privte 다른곳 외부에서 접근 불가능 하게 하기 위하여.
    private int mImageRes; //List Image
    private String mChkPosition;
    private String mTabTitle; //TabList 제목
    private String mDetailInfo1; //상세페이지 정보
    private String mDetailInfo2;
    private String mDetailInfo3;
    private String mDetailInfo4;
    private String mDetailInfo5;
    private String mDetailInfo6;
    private String mTabCode;

    public TravelDataModel(int imageRes,String chkPosition, String tabTitle, String detailInfo1, String detailInfo2, String detailInfo3, String detailInfo4, String detailInfo5, String detailInfo6, String tabCode) {
        this.mImageRes = imageRes;
        this.mChkPosition = chkPosition;
        this.mTabTitle = tabTitle;
        this.mDetailInfo1 = detailInfo1;
        this.mDetailInfo2 = detailInfo2;
        this.mDetailInfo3 = detailInfo3;
        this.mDetailInfo4 = detailInfo4;
        this.mDetailInfo5 = detailInfo5;
        this.mDetailInfo6 = detailInfo6;
        this.mTabCode = tabCode;
    }

    public TravelDataModel(ArrayList<TravelDataModel> travelDataModel, int position) {
        this.mChkPosition    = travelDataModel.get(position).getmChkPosition();
        this.mTabTitle       = travelDataModel.get(position).getmTabTitle();
        this.mDetailInfo1    = travelDataModel.get(position).getmDetailInfo1();
        this.mDetailInfo2    = travelDataModel.get(position).getmDetailInfo2();
        this.mDetailInfo3    = travelDataModel.get(position).getmDetailInfo3();
        this.mDetailInfo4    = travelDataModel.get(position).getmDetailInfo4();
        this.mDetailInfo5    = travelDataModel.get(position).getmDetailInfo5();
        this.mDetailInfo6    = travelDataModel.get(position).getmDetailInfo6();
        this.mTabCode         = travelDataModel.get(position).getmTabCode();
    }

    //Parcelable를 생성하기 위한 생성자 Parcel를 파라메타로 넘겨 받음
    //EventLisModel.java에 모든 parcel된 데이터를 복구하는 생성자를 정의해 줘야만 한다.
    public TravelDataModel(Parcel source) {
        mImageRes = source.readInt();
        mChkPosition = source.readString();
        mTabTitle = source.readString();
        mDetailInfo1 = source.readString();
        mDetailInfo2 = source.readString();
        mDetailInfo3 = source.readString();
        mDetailInfo4 = source.readString();
        mDetailInfo5 = source.readString();
        mDetailInfo6 = source.readString();
        mTabCode = source.readString();

    }

    public int getmImageRes() {
        return mImageRes;
    }
    public  void setmImageRes(int imageRes) {
        this.mImageRes = imageRes;
    }

    public String getmTabTitle() {
        return mTabTitle;
    }
    public void setmTabTitle(String tabTitle) {
        this.mTabTitle = tabTitle;
    }

    public String getmDetailInfo1() {
        return mDetailInfo1;
    }
    public void setmDetailInfo1(String detailInfo1) {
        this.mDetailInfo1 = detailInfo1;
    }

    public String getmDetailInfo2() {
        return mDetailInfo2;
    }
    public void setmDetailInfo2(String detailInfo2) {
        this.mDetailInfo2 =  detailInfo2;
    }

    public String getmDetailInfo3() {
        return mDetailInfo3;
    }
    public void setmDetailInfo3(String detailInfo3) {
        this.mDetailInfo3 = detailInfo3;
    }

    public String getmDetailInfo4() {
        return mDetailInfo4;
    }
    public void setmDetailInfo4(String detailInfo4) {
        this.mDetailInfo4 = detailInfo4;
    }

    public String getmDetailInfo5() {
        return mDetailInfo5;
    }
    public void setmDetailInfo5(String detailInfo5) {
        this.mDetailInfo5 = detailInfo5;
    }

    public String getmDetailInfo6() {
        return mDetailInfo6;
    }
    public void setmDetailInfo6(String detailInfo6) {
        this.mDetailInfo6 = detailInfo6;
    }

    public String getmTabCode() {
        return mTabCode;
    }
    public void setmTabCode(String tabCode) {
        this.mTabCode = tabCode;
    }

    public String getmChkPosition() {
        return mChkPosition;
    }
    public void setmChkPosition(String chkPosition) {
        this.mChkPosition = chkPosition;
    }
    //Parcelable을 상속 받으면 필수 Method
    //- Parcel 하려는 오브젝트의 종류를 정의한다
    @Override
    public int describeContents() {
        return 0;
    }
    //Parcelable의 write를 구현하기 위한 Method
    //- 실제 오브젝트 serialization/flattening을 하는 메소드. 오브젝트의 각 엘리먼트를 각각 parcel해줘야 한다.
    // 주의 할 점은 반드시 생성자에서 읽어오는 순서와, 기록하는 순서가 같아야 한다.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImageRes);
        dest.writeString(mChkPosition);
        dest.writeString(mTabTitle);
        dest.writeString(mDetailInfo1);
        dest.writeString(mDetailInfo2);
        dest.writeString(mDetailInfo3);
        dest.writeString(mDetailInfo4);
        dest.writeString(mDetailInfo5);
        dest.writeString(mDetailInfo6);
        dest.writeString(mTabCode);
    }

    //Parcelable 객체로 구현하기 위한 Parcelable Method ArrayList구현 등..
    public static final Parcelable.Creator<TravelDataModel> CREATOR
            = new Parcelable.Creator<TravelDataModel>() {

        @Override
        public TravelDataModel createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new TravelDataModel(source);
        }

        @Override
        public TravelDataModel[] newArray(int size) {
            // TODO Auto-generated method stub
            return new TravelDataModel[size];
        }
    };
}
