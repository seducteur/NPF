package com.seducteur.npf.activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.seducteur.npf.R;
import com.seducteur.npf.adapter.TravelInfoAdapter;
import com.seducteur.npf.model.TravelInfoModel;
import com.seducteur.npf.model.TravelDataModel;
import com.seducteur.npf.url.Url;


/**
 * Created by x-note on 2016-03-06.
 */
public class EventListActivity extends AppCompatActivity {
    //private 외부에서 접근하지 못하게
    private ListView mListView;
    private Connection connection;
    private StringBuilder mSbuilder;
    private URL mUrl = null;
    private HttpURLConnection mConn = null;
    private JSONArray mArray;
    private ArrayList<TravelDataModel> mListData = null;
    private TravelInfoAdapter mAdapter = null;
    private TravelDataModel mEventListModel;
    private TravelInfoModel mAreaListModel;
    private ArrayList<TravelInfoModel> mAreaList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlist_layout);
        connection = new Connection();
        connection.execute();
        initialize();
    }

    private void initialize() {
        initializeFields();
        initializeListeners();
        initializeView();
    }

    private void initializeFields() {
        mListView = (ListView) findViewById(R.id.list_view);
    }

    private void initializeListeners() {

    }

    private void initializeView() {
        ActionBar actionBar = getSupportActionBar();

        /*getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));//actionbar 색상*/
        //Custom Actionbar 사용하기 위해 CustomEnabled를 true 시키고 필요없는 것들 체크
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false); //홈아이콘을 클릭햇을 때 어떤 처리를 하고싶다면 true
        actionBar.setDisplayShowTitleEnabled(false); //타이틀을 보여줄지 말지

        // Custom View Layout Set (ActionBar)
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.main_actionbar_custom, null);
        actionBar.setCustomView(mCustomView, new ActionBar.LayoutParams(Gravity.CENTER)); //ActionBar Set & 레이아웃 가운데 정렬(레이아웃 ActionBar.LayoutParams.WRAP_CONTENT 식으로 레이아웃 수정할 수 있음)
        // ActionBar Color
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position) + "selected", Toast.LENGTH_SHORT).show();

                        /*mEventListModel = new EventListModel(mListData, position); // List와 position 생성자로 던져주고 position 번째 데이터만 가져옴.
                        Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                        intent.putExtra("selectedData", mEventListModel); //position번째만 가져온 data Model 을 intent 함
                        startActivity(intent);*/
                Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                intent.putExtra("selectedArea", mAreaList.get(position).getmArea());
                Log.d("selectedArea", "= " + mAreaList.get(position).getmArea());
                startActivity(intent);
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
                mUrl = new URL(Url.EVENT_LIST); //연결할url
                mConn = (HttpURLConnection) mUrl.openConnection();

                if (mConn != null) {
                    mConn.setConnectTimeout(30000);
                    mConn.setReadTimeout(30000);
                    mConn.setRequestMethod("GET");
                    mConn.setDoInput(true);
                    mConn.setDoOutput(true);;
                    mConn.setUseCaches(false);
                    Log.d("코드", "code = " +mConn.getResponseCode());
                    Log.d("메세지", "msg = " + mConn.getResponseMessage());
                    if (mConn.getResponseCode() == 200) { // 성공이면

                        BufferedReader reader = new BufferedReader(new InputStreamReader(mConn.getInputStream(), "UTF-8"));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            mSbuilder.append(line);
                        }
                        Log.d("값" , " = " + mSbuilder);

                        reader.close();

                    }

                    mConn.disconnect();
                }
            } catch (Exception e) {
                Log.d("리스트페이지", "리스트 에러메세지" + e.getMessage());
                mConn.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("리스트페이지", "리스트 리턴 값 : " + mSbuilder + "");
            JsonProcessing(mSbuilder);
        }

        //json 파서
        public void JsonProcessing(StringBuilder sb) {

            try {
                mArray = new JSONArray(sb.toString());

                int length = mArray.length(); // 데이터 갯수 만큼만 리스트 만들기위해서

               // mEventList = new ArrayList<String>(length);
                mListData = new ArrayList<>(length);
                mAreaList = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    /*mListData.add(new EventListModel(R.drawable.picture, Array.getJSONObject(i).getString("list"), Array.getJSONObject(i).getString("recommendation"), Array.getJSONObject(i).getString("bestfood"), Array.getJSONObject(i).getString("festival"), Array.getJSONObject(i).getString("detailTitle")));*/
                    mAreaList.add(new TravelInfoModel(R.drawable.picture, mArray.getJSONObject(i).getString("area")));
                }

                mAdapter = new TravelInfoAdapter(EventListActivity.this, mAreaList);
                mListView.setAdapter(mAdapter);

            } catch (Exception e) {
                Log.d("리스트페이지", "json파서 error" + e.getMessage());
            }
        }

    }

}
