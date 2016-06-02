package com.seducteur.npf.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seducteur.npf.R;
import com.seducteur.npf.adapter.TravelDataAdapter;
import com.seducteur.npf.model.TravelDataModel;
import com.seducteur.npf.url.Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by x-note on 2016-03-12.
 */
public class EventDetailActivity extends AppCompatActivity {

    private final static int TEXT_SIZE = 17;
    private final static int TEXT_COLOR = Color.parseColor("#616161");
    private SharedPreferences mPref;
    private SharedPreferences.Editor mPrefEdit;
    private TextView mTitleTxt;
    private ImageView mDetailView;
    private Button mFavBtn;
    private TravelDataModel mTravelDataModel;
    private LinearLayout mSubjectLayout, mContentsLayout;
    private LinearLayout.LayoutParams mSubjectParams, mContentsParams;
    private TextView mSubjectTxt1, mSubjectTxt2, mSubjectTxt3, mSubjectTxt4, mSubjectTxt5;
    private TextView mContentsTxt1, mContentsTxt2, mContentsTxt3, mContentsTxt4, mContentsTxt5;
    private String mChkPostion = "";
    private String mChkResult = "";
    private Connection connection;
    private URL mUrl = null;
    private HttpURLConnection mConn = null;
    private StringBuilder mSbuilder;
    private JSONArray mArray;
    private String mCallChk = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_layout);
        initialize();
    }
    // 앱 종료후 다시 시작했을때에 즐겨찾기 버튼 추가/해제 유지하기 위해
    @Override
    protected void onResume() {
        super.onResume();
       /*// SharedPreferences pref = getSharedPreferences("Pref1", 0);
        //0 해제 1 추가 버튼 이미지 변경
        if(mPref.getInt("using", 0) == 0) {
            mFavBtn.setSelected(false);
            Log.d("즐겨찾기해제", ":");
        } else {
            mFavBtn.setSelected(true);
            Log.d("즐겨찾기추가", ":");
        }*/
        mCallChk = "0";
        connection = new Connection();
        connection.execute();
    }

    private void initialize() {
        initializeFields();
        initializeListeners();
        initializeView();
    }

    private void initializeFields() {
        /*//프리퍼런스 객체 생성
        mPref = getSharedPreferences("Pref1", 0); //생성자의 변수로 들어가는 값은 ("프레퍼런스 파일 이름", "읽기쓰기모드"); // 0은 읽기,쓰기 모두 가능한 모드
        //프리퍼런스에 내용을 쓰거나 수정하게 해주는 에디터 생성
        mPrefEdit = mPref.edit();*/
        mFavBtn = (Button) findViewById(R.id.fav_btn);
        mTitleTxt = (TextView) findViewById(R.id.title);
        mDetailView = (ImageView) findViewById(R.id.deailView);
        mSubjectLayout = (LinearLayout) findViewById(R.id.subject);
        mContentsLayout = (LinearLayout) findViewById(R.id.contents);
        mSubjectTxt1 = new TextView(EventDetailActivity.this);
        mSubjectTxt2 = new TextView(EventDetailActivity.this);
        mSubjectTxt3 = new TextView(EventDetailActivity.this);
        mSubjectTxt4 = new TextView(EventDetailActivity.this);
        mSubjectTxt5 = new TextView(EventDetailActivity.this);
        mContentsTxt1 = new TextView(EventDetailActivity.this);
        mContentsTxt2 = new TextView(EventDetailActivity.this);
        mContentsTxt3 = new TextView(EventDetailActivity.this);
        mContentsTxt4 = new TextView(EventDetailActivity.this);
        mContentsTxt5 = new TextView(EventDetailActivity.this);

        //layout_width, layout_height, gravity 설정
        mSubjectParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mSubjectParams.setMargins(50, 20, 20, 5);

        mContentsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mContentsParams.setMargins(60, 0, 20, 20);

        mTravelDataModel = getIntent().getParcelableExtra("detailData");
        Log.d("detailReceiveData", "= " + mTravelDataModel.getmTabTitle() + "/ "  + mTravelDataModel.getmDetailInfo1());

        mChkPostion = mTravelDataModel.getmChkPosition();

    }

    private void initializeListeners() {
        //mFavBtn.setOnClickListener(this);
    }

    /*@Override
    public void onClick(View v) {
        //에디터 객체 값을 저장하고, 실제 파일에 저장
        //using 0 추가 1 해제
        if(mPref.getInt("using", 0) == 0) {
            mPrefEdit.putString("TITLEDATA", mTitleTxt.getText().toString()); //임시저장
            mPrefEdit.putInt("using", 1);
            mPrefEdit.commit(); //반드시 commit()으로 파일에 실제로 저장
            mFavBtn.setBackgroundResource(R.drawable.ic_star_black_36dp);
            Toast.makeText(getBaseContext(), "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
        } else {
            mPrefEdit.putString("TITLEDATA", "");
            mPrefEdit.putInt("using", 0);
            mPrefEdit.commit();
            mFavBtn.setBackgroundResource(R.drawable.ic_menu_star);
            Toast.makeText(getBaseContext(), "즐겨찾기 해제", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void initializeView() {

        /** ActionBar Setting **/
        ActionBar actionBar = getSupportActionBar();

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


        /** Detail page Data Setting **/
        if(mTravelDataModel.getmTabCode().equals("01")) {
            mTitleTxt.setText(mTravelDataModel.getmDetailInfo1());
            mDetailView.setImageResource(R.drawable.picture2);
            if(!mTravelDataModel.getmDetailInfo2().equals("")) {
                mSubjectTxt1.setText("위치");
                mSubjectTxt1.setTextSize(TEXT_SIZE);
                mSubjectTxt1.setTextColor(TEXT_COLOR);
                mSubjectTxt1.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt1);

                mContentsTxt1.setText(mTravelDataModel.getmDetailInfo2());
                mContentsTxt1.setTextSize(TEXT_SIZE);
                mContentsTxt1.setTextColor(TEXT_COLOR);
                mContentsTxt1.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt1);
            }
            if(!mTravelDataModel.getmDetailInfo3().equals("")) {
                mSubjectTxt2.setText("안내전화");
                mSubjectTxt2.setTextSize(TEXT_SIZE);
                mSubjectTxt2.setTextColor(TEXT_COLOR);
                mSubjectTxt2.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt2);

                mContentsTxt2.setText(mTravelDataModel.getmDetailInfo3());
                mContentsTxt2.setTextSize(TEXT_SIZE);
                mContentsTxt2.setTextColor(TEXT_COLOR);
                mContentsTxt2.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt2);
            }
            if(!mTravelDataModel.getmDetailInfo4().equals("")) {
                mSubjectTxt3.setText("이용요금");
                mSubjectTxt3.setTextSize(TEXT_SIZE);
                mSubjectTxt3.setTextColor(TEXT_COLOR);
                mSubjectTxt3.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt3);

                mContentsTxt3.setText(mTravelDataModel.getmDetailInfo4());
                mContentsTxt3.setTextSize(TEXT_SIZE);
                mContentsTxt3.setTextColor(TEXT_COLOR);
                mContentsTxt3.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt3);
            }
            if(!mTravelDataModel.getmDetailInfo5().equals("")) {
                mSubjectTxt4.setText("이용기간");
                mSubjectTxt4.setTextSize(TEXT_SIZE);
                mSubjectTxt4.setTextColor(TEXT_COLOR);
                mSubjectTxt4.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt4);

                mContentsTxt4.setText(mTravelDataModel.getmDetailInfo5());
                mContentsTxt4.setTextSize(TEXT_SIZE);
                mContentsTxt4.setTextColor(TEXT_COLOR);
                mContentsTxt4.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt4);
            }
            if(!mTravelDataModel.getmDetailInfo6().equals("")) {
                mSubjectTxt5.setText("홈페이지");
                mSubjectTxt5.setTextSize(TEXT_SIZE);
                mSubjectTxt5.setTextColor(TEXT_COLOR);
                mSubjectTxt5.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt5);

                mContentsTxt5.setText(mTravelDataModel.getmDetailInfo6());
                mContentsTxt5.setTextSize(TEXT_SIZE);
                mContentsTxt5.setTextColor(TEXT_COLOR);
                mContentsTxt5.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt5);
            }
        } else if (mTravelDataModel.getmTabCode().equals("02")) {
            mTitleTxt.setText(mTravelDataModel.getmDetailInfo1());
            mDetailView.setImageResource(R.drawable.picture2);
            if(!mTravelDataModel.getmDetailInfo2().equals("")) {
                mSubjectTxt1.setText("위치");
                mSubjectTxt1.setTextSize(TEXT_SIZE);
                mSubjectTxt1.setTextColor(TEXT_COLOR);
                mSubjectTxt1.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt1);

                mContentsTxt1.setText(mTravelDataModel.getmDetailInfo2());
                mContentsTxt1.setTextSize(TEXT_SIZE);
                mContentsTxt1.setTextColor(TEXT_COLOR);
                mContentsTxt1.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt1);
            }
            if(!mTravelDataModel.getmDetailInfo3().equals("")) {
                mSubjectTxt2.setText("안내전화");
                mSubjectTxt2.setTextSize(TEXT_SIZE);
                mSubjectTxt2.setTextColor(TEXT_COLOR);
                mSubjectTxt2.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt2);

                mContentsTxt2.setText(mTravelDataModel.getmDetailInfo3());
                mContentsTxt2.setTextSize(TEXT_SIZE);
                mContentsTxt2.setTextColor(TEXT_COLOR);
                mContentsTxt2.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt2);
            }
            if(!mTravelDataModel.getmDetailInfo4().equals("")) {
                mSubjectTxt3.setText("이용기간");
                mSubjectTxt3.setTextSize(TEXT_SIZE);
                mSubjectTxt3.setTextColor(TEXT_COLOR);
                mSubjectTxt3.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt3);

                mContentsTxt3.setText(mTravelDataModel.getmDetailInfo4());
                mContentsTxt3.setTextSize(TEXT_SIZE);
                mContentsTxt3.setTextColor(TEXT_COLOR);
                mContentsTxt3.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt3);
            }
            if(!mTravelDataModel.getmDetailInfo5().equals("")) {
                mSubjectTxt4.setText("이용정보");
                mSubjectTxt4.setTextSize(TEXT_SIZE);
                mSubjectTxt4.setTextColor(TEXT_COLOR);
                mSubjectTxt4.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt4);

                mContentsTxt4.setText(mTravelDataModel.getmDetailInfo5());
                mContentsTxt4.setTextSize(TEXT_SIZE);
                mContentsTxt4.setTextColor(TEXT_COLOR);
                mContentsTxt4.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt4);
            }
        } else {
            mTitleTxt.setText(mTravelDataModel.getmDetailInfo1());
            mDetailView.setImageResource(R.drawable.picture2);
            if(!mTravelDataModel.getmDetailInfo2().equals("")) {
                mSubjectTxt1.setText("위치");
                mSubjectTxt1.setTextSize(TEXT_SIZE);
                mSubjectTxt1.setTextColor(TEXT_COLOR);
                mSubjectTxt1.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt1);

                mContentsTxt1.setText(mTravelDataModel.getmDetailInfo2());
                mContentsTxt1.setTextSize(TEXT_SIZE);
                mContentsTxt1.setTextColor(TEXT_COLOR);
                mContentsTxt1.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt1);
            }
            if(!mTravelDataModel.getmDetailInfo3().equals("")) {
                mSubjectTxt2.setText("안내전화");
                mSubjectTxt2.setTextSize(TEXT_SIZE);
                mSubjectTxt2.setTextColor(TEXT_COLOR);
                mSubjectTxt2.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt2);

                mContentsTxt2.setText(mTravelDataModel.getmDetailInfo3());
                mContentsTxt2.setTextSize(TEXT_SIZE);
                mContentsTxt2.setTextColor(TEXT_COLOR);
                mContentsTxt2.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt2);
            }
            if(!mTravelDataModel.getmDetailInfo4().equals("")) {
                mSubjectTxt3.setText("이용요금");
                mSubjectTxt3.setTextSize(TEXT_SIZE);
                mSubjectTxt3.setTextColor(TEXT_COLOR);
                mSubjectTxt3.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt3);

                mContentsTxt3.setText(mTravelDataModel.getmDetailInfo4());
                mContentsTxt3.setTextSize(TEXT_SIZE);
                mContentsTxt3.setTextColor(TEXT_COLOR);
                mContentsTxt3.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt3);
            }
            if(!mTravelDataModel.getmDetailInfo5().equals("")) {
                mSubjectTxt4.setText("이용기간");
                mSubjectTxt4.setTextSize(TEXT_SIZE);
                mSubjectTxt4.setTextColor(TEXT_COLOR);
                mSubjectTxt4.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt4);

                mContentsTxt4.setText(mTravelDataModel.getmDetailInfo5());
                mContentsTxt4.setTextSize(TEXT_SIZE);
                mContentsTxt4.setTextColor(TEXT_COLOR);
                mContentsTxt4.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt4);
            }
            if(!mTravelDataModel.getmDetailInfo6().equals("")) {
                mSubjectTxt5.setText("소개");
                mSubjectTxt5.setTextSize(TEXT_SIZE);
                mSubjectTxt5.setTextColor(TEXT_COLOR);
                mSubjectTxt5.setLayoutParams(mSubjectParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mSubjectTxt5);

                mContentsTxt5.setText(mTravelDataModel.getmDetailInfo6());
                mContentsTxt5.setTextSize(TEXT_SIZE);
                mContentsTxt5.setTextColor(TEXT_COLOR);
                mContentsTxt5.setLayoutParams(mContentsParams);
                //부모 뷰에 추가
                mContentsLayout.addView(mContentsTxt5);
            }

        }

        /** Favorite Button **/
        /*mFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //에디터 객체 값을 저장하고, 실제 파일에 저장
                //using 0 추가 1 해제
                if (mPref.getInt("using", 0) == 0) { //처음에 using이란거 만들면서 아무값도 안잡혀있어서 0으로 지정해놓고 0이라 생각하면됨
                    // mPrefEdit.putString("TITLEDATA", mTitleTxt.getText().toString()); //임시저장
                    mPrefEdit.putInt("using", 1);
                    mPrefEdit.commit(); //반드시 commit()으로 파일에 실제로 저장
                    mFavBtn.setSelected(true);
                    Toast.makeText(getBaseContext(), "즐겨찾기 추가", Toast.LENGTH_SHORT).show();
                } else {
                    // mPrefEdit.putString("TITLEDATA", "");
                    mPrefEdit.putInt("using", 0);
                    mPrefEdit.commit();
                    mFavBtn.setSelected(false);
                    Toast.makeText(getBaseContext(), "즐겨찾기 해제", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /** Favorite Button **/
        mFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallChk = "1";
                connection = new Connection();
                connection.execute();
            }
        });

    }

    private class Connection extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
        }

        //httpurlconnection 통신 asynctask 앱 터진다고하여 사용. httpurlconnection선택은 어느 버전이후로 httpconnection이랑은 지원하지 않는다하여
        @Override
        protected Void doInBackground(Void... params) {

            mSbuilder = new StringBuilder();

            try {
                mUrl = new URL(Url.FAV_LIST); //연결할url
                mConn = (HttpURLConnection) mUrl.openConnection();

                if (mConn != null) {
                    mConn.setConnectTimeout(30000);
                    mConn.setReadTimeout(30000);
                    mConn.setRequestMethod("POST");
                    mConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 헤더값을 설정한다.(인코딩해서 보내는거)
                    mConn.setDoInput(true);
                    mConn.setDoOutput(true);;
                    mConn.setUseCaches(false);

                    String param  = "callChk="+mCallChk+"&position="+mChkPostion;  // POST방식이면 서버에 별도의 파라메터값을 넘겨주어야 한다.
                    OutputStream os = mConn.getOutputStream();
                    os.write(param.getBytes("UTF-8"));
                    os.flush();
                    os.close();
                    Log.d("Detail", "code = " +mConn.getResponseCode());
                    Log.d("Detail", "msg = " + mConn.getResponseMessage());
                    if (mConn.getResponseCode() == 200) { // 성공이면

                        BufferedReader reader = new BufferedReader(new InputStreamReader(mConn.getInputStream(), "UTF-8"));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            mSbuilder.append(line);
                        }
                        Log.d("Detail" , " 값= " + mSbuilder);

                        reader.close();

                    }

                    mConn.disconnect();
                }
            } catch (Exception e) {
                Log.d("Detail", "리스트 에러메세지" + e.getMessage());
                mConn.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("Detail", "리스트 리턴 값 : " + mSbuilder + "");
            JsonProcessing(mSbuilder);
        }

        //json 파서
        public void JsonProcessing(StringBuilder sb) {

            try {
                mArray = new JSONArray(sb.toString());

                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject jObj = mArray.getJSONObject(i);
                    mChkResult = jObj.getString("chkRtn");

                }

                if(mChkResult.equals("0")) {
                    mFavBtn.setSelected(false);
                } else {
                    mFavBtn.setSelected(true);
                }

            } catch (Exception e) {
                Log.d("Detail", "json파서 error" + e.getMessage());
            }
        }

    }

}
