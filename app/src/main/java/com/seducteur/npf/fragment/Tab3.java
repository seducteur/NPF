package com.seducteur.npf.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.seducteur.npf.activity.EventActivity;
import com.seducteur.npf.R;
import com.seducteur.npf.activity.EventDetailActivity;
import com.seducteur.npf.adapter.TravelDataAdapter;
import com.seducteur.npf.adapter.TravelInfoAdapter;
import com.seducteur.npf.model.TravelDataModel;
import com.seducteur.npf.url.Url;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by x-note on 2016-03-04.
 */
public class Tab3 extends Fragment {
    private TravelDataAdapter mAdapter = null;
    private URL mUrl = null;
    private HttpURLConnection mConn = null;
    private ArrayList<TravelDataModel> mFestivalList = null;
    private Connection connection;
    private ListView mListView;
    private LinearLayout mFrag1;
    private EventActivity mEventActivity;
    private TravelDataModel mTravelDataModel;
    private String mReceiveArea;
    private StringBuilder mSbuilder;
    private JSONArray mArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null) {
            return null;
        }
        mFrag1 = (LinearLayout) inflater.inflate(R.layout.tab3_layout, container, false);
        initialize();
        return mFrag1;
    }

    private void initialize() {
        initializeFields();
        initializeListeners();
        initializeView();
    }

    private void initializeFields() {
        mListView = (ListView) mFrag1.findViewById(R.id.list_view);
    }

    private void initializeListeners() {

    }

    private void initializeView() {

        mEventActivity = (EventActivity)getActivity(); //fragment 의 Activity(EventActivity) 액티비티를 가져옴  프래그먼트에서 액티비티를 가져올수 있어서
        mReceiveArea = mEventActivity.getIntent().getStringExtra("selectedArea");
        Log.d("receiveArea", "= " + mReceiveArea);

        connection = new Connection();
        connection.execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), parent.getItemIdAtPosition(position) + "selected", Toast.LENGTH_SHORT).show();

                mTravelDataModel = new TravelDataModel(mFestivalList, position); // List와 position 생성자로 던져주고 position 번째 데이터만 가져옴.
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra("detailData", mTravelDataModel); //모델 intent
                Log.d("selectedArea", "= " + mFestivalList.get(position).getmDetailInfo1());
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
                mUrl = new URL(Url.FESTIVAL_LIST); //연결할url
                mConn = (HttpURLConnection) mUrl.openConnection();

                if (mConn != null) {
                    mConn.setConnectTimeout(30000);
                    mConn.setReadTimeout(30000);
                    mConn.setRequestMethod("POST");
                    mConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 헤더값을 설정한다.(인코딩해서 보내는거)
                    mConn.setDoInput(true);
                    mConn.setDoOutput(true);;
                    mConn.setUseCaches(false);

                    String param  = "area="+mReceiveArea;  // POST방식이면 서버에 별도의 파라메터값을 넘겨주어야 한다.
                    OutputStream os = mConn.getOutputStream();
                    os.write(param.getBytes("UTF-8"));
                    os.flush();
                    os.close();
                    Log.d("TabFragment", "code = " +mConn.getResponseCode());
                    Log.d("TabFragment", "msg = " + mConn.getResponseMessage());
                    if (mConn.getResponseCode() == 200) { // 성공이면

                        BufferedReader reader = new BufferedReader(new InputStreamReader(mConn.getInputStream(), "UTF-8"));
                        String line = "";
                        while ((line = reader.readLine()) != null) {
                            mSbuilder.append(line);
                        }
                        Log.d("TabFragment" , " 값= " + mSbuilder);

                        reader.close();

                    }

                    mConn.disconnect();
                }
            } catch (Exception e) {
                Log.d("TabFragment", "리스트 에러메세지" + e.getMessage());
                mConn.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("TabFragment", "리스트 리턴 값 : " + mSbuilder + "");
            JsonProcessing(mSbuilder);
        }

        //json 파서
        public void JsonProcessing(StringBuilder sb) {

            try {
                mArray = new JSONArray(sb.toString());

                int length = mArray.length(); // 데이터 갯수 만큼만 리스트 만들기위해서

                mFestivalList = new ArrayList<>(length);

                for (int i = 0; i < length; i++) {
                    /*mListData.add(new EventListModel(R.drawable.picture, Array.getJSONObject(i).getString("list"), Array.getJSONObject(i).getString("recommendation"), Array.getJSONObject(i).getString("bestfood"), Array.getJSONObject(i).getString("festival"), Array.getJSONObject(i).getString("detailTitle")));*/
                    mFestivalList.add(new TravelDataModel(R.drawable.picture, mArray.getJSONObject(i).getString("chkPosition"), mArray.getJSONObject(i).getString("festival"), mArray.getJSONObject(i).getString("title"), mArray.getJSONObject(i).getString("location"), mArray.getJSONObject(i).getString("telNo"), mArray.getJSONObject(i).getString("money"), mArray.getJSONObject(i).getString("term"), mArray.getJSONObject(i).getString("introduce"), mArray.getJSONObject(i).getString("tabCode")));
                }

                mAdapter = new TravelDataAdapter(getActivity(), mFestivalList);
                mListView.setAdapter(mAdapter);

            } catch (Exception e) {
                Log.d("TabFragment", "json파서 error" + e.getMessage());
            }
        }

    }

}