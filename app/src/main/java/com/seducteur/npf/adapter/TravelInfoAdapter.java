package com.seducteur.npf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seducteur.npf.R;
import com.seducteur.npf.model.TravelInfoModel;

import java.util.ArrayList;


public class TravelInfoAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<TravelInfoModel> mListData;

    private final LayoutInflater mInflater;

    // Context = 액티비티의 부모 , context는 리소스에 접근하게 해줌 context를 받음 아니면 그냥 class로는 리소스에 접근 불가능
    public TravelInfoAdapter(Context context, ArrayList<TravelInfoModel> listData) {
        mContext = context;
        mListData = listData;

        // 레이아웃을 가져오게 하는 객체
        // Activity가 findViewbyid 가되는거는 내부적으로 layoutinflater를 통해서 가능 그래서 layoutinflater는 context를 가지고있으면 거기서부터 가져올수 있다.
        mInflater = LayoutInflater.from(mContext);
    }

    // 아이템의 갯수
    @Override
    public int getCount() {
        return mListData.size();
    }

    // 포지션번째 아이템 리턴
    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    // 포지션번째 아이템 id 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }


    // 아이템 한개의 View를 완성하는 곳
    // getview 비용이 많이드는애 새로해도, 안해도 이걸 줄이기 위해 제공하는게 viewholder 패턴 계속생성하는걸 한번만 하게끔. 한번 생성된걸 계속 부르지 않도록
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // 최초 view가 생겼을때 아무것도 없다 어떤 position이든 null로 들어온다.
        if (convertView == null) {
            // 최초 로드할 때
            holder = new ViewHolder();

            // 레이이아웃을 가져오 하는 객체 레이아웃, viewGrouproot(부모), attachroot(false) = 부모에 붙힐꺼냐 true로 붙히면 listview_item이 부모가된다
            convertView = mInflater.inflate(R.layout.travellist_item_layout, parent, false);

            //데이터를 연결할 View (convertView == null 일때 처음에 한번만 하자고 함)
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            TextView  area      = (TextView) convertView.findViewById(R.id.addrTxt);

            // 홀더에 가져온 것을 담는다
            holder.mImage = imageView;
            holder.mArea = area;

            // viewclass의 setTag 기능 / 뷰에다가 값을담을 수 있는 기능, 컨버트뷰에 홀드시켜놓는것
            convertView.setTag(holder);
        } else {
            // 재활용 getTag로 가져와서 다시 쓰는것
            holder = (ViewHolder) convertView.getTag();
        }

        /** list Data 가져와서 설정하는 부분 **/
        //데이터 getItem을 통해서 가져옴
        TravelInfoModel item = (TravelInfoModel) getItem(position); // ListData item = mData.get(position)으로도 가능

        //데이터 설정
        if(!item.equals(null) || !item.equals("")) {
            holder.mImage.setImageResource(item.getmImageRes());
            holder.mArea.setText(item.getmArea());
        }
        // convertview를 완성해서 리턴턴
        return convertView;
    }

    // 리스트의 img,txt등 이것들 갖고있는 역할
    private static class ViewHolder {
        ImageView mImage;

        TextView mArea;
    }
}