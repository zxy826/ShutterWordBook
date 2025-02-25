package org.androidtown.shutterwordbook.Class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import org.androidtown.shutterwordbook.Activity.ContentActivity;
import org.androidtown.shutterwordbook.Activity.StartActivity;
import org.androidtown.shutterwordbook.R;

import java.util.ArrayList;

/**
 * Created by ehye on 2015-08-17.
 */
public class AdapterWordbook extends ArrayAdapter<ItemWordbook> {

    // 레이아웃 XML을 읽어들이기 위한 객체
    public LayoutInflater mInflater;
    Context context;
    public AdapterWordbook(Context context, ArrayList<ItemWordbook> object) {

        // 상위 클래스의 초기화 과정
        // context, 0, 자료구조
        super(context, 0, object);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = context;


    }
    // 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        View view = null;
        // 현재 리스트의 하나의 항목에 보일 컨트롤 얻기
        if (v == null) {
            view = mInflater.inflate(R.layout.item_wordbook, null);
        } else {
            view = v;
        }
        // 자료를 받음
        final ItemWordbook data = this.getItem(position);

        if(data != null){
            // 화면 출력
              TextView textViewWordbookName = (TextView) view.findViewById(R.id.textView_wordbook_name);

//            checkBoxWordbookName.setFocusable(false);
            textViewWordbookName.setText(data.getWordbookName());

            textViewWordbookName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = data.getWordbookName();

                    Bundle bundleWordbook = new Bundle();
                    bundleWordbook.putString("wordbookName", name);


                    Intent intent = new Intent(getContext(), ContentActivity.class);
                    intent.putExtras(bundleWordbook);
                   getContext().startActivity(intent);
                }
            });
        }



        return view;
    }




}
