package org.androidtown.shutterwordbook.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.androidtown.shutterwordbook.R;

import java.util.ArrayList;

/**
 * Created by ehye on 2015-08-07.
 */

public class AdapterWordbookContent extends ArrayAdapter<ItemWordbookContent> {
    // 레이아웃 XML을 읽어들이기 위한 객체
    public LayoutInflater mInflater;

    public AdapterWordbookContent(Context context, ArrayList<ItemWordbookContent> object) {

        // 상위 클래스의 초기화 과정
        // context, 0, 자료구조
        super(context, 0, object);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    // 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View view = null;
        // 현재 리스트의 하나의 항목에 보일 컨트롤 얻기
        if (v == null) {
            view = mInflater.inflate(R.layout.item_wordbookcontent, null);
        } else {
            view = v;
        }
        // 자료를 받음
        final ItemWordbookContent data = this.getItem(position);

        if(data != null){
            // 화면 출력
            TextView textViewWord = (TextView) view.findViewById(R.id.textView_word2);
            TextView textViewMean=(TextView) view.findViewById(R.id.textView_mean2);

            // 텍스트뷰1에 getLabel()을 출력 즉 첫번째 인수값
            textViewWord.setText(data.getWord());
            textViewMean.setText(data.getMean());
            // textView2.setTextColor(COLOR.WHITE);
        }
        return view;
    }

}