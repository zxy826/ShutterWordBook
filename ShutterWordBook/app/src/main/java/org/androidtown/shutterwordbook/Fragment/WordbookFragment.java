package org.androidtown.shutterwordbook.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import org.androidtown.shutterwordbook.Helper.DictionaryOpenHelper;
import org.androidtown.shutterwordbook.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordbookFragment extends Fragment {


    //
    //  DB 관련
    private SQLiteDatabase db;
    DictionaryOpenHelper dbHelper;

    //

    private ListView listWordbooks;  // 단어장 리스트
    private ArrayAdapter<String> adapter;
    private ArrayList wordbooks;
//
    // Fragment와 통신하는 부분
        AccidentListener mCallback;
    //

    /* Start of onCreate View*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_wordbook, container, false);


        listWordbooks = (ListView) rootView.findViewById(R.id.listView_wordbooks);
        wordbooks = new ArrayList<String >();
       boolean isOpen = openDatabase();
        if(isOpen){
            initList();
        }
//
        //

        //
        listWordbooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                String wordbookName =  parent.getItemAtPosition(position).toString();

                mCallback.showWordbook(wordbookName);

            } catch (Exception e){
                Log.d("WordbookFrag", "click error " + e.toString());
            }
        }
    });


      return rootView;
    }
/*  End of onCreateView()*/



    /* 데이터베이스 열기 */
    public boolean openDatabase(){
        System.out.println("opening database");
        dbHelper = new DictionaryOpenHelper(getActivity());
        //    db = dbHelper.getWritableDatabase();
        return true;
    }

    //

    private void initList(){
        try {
            boolean is =openDatabase();
            db = dbHelper.getReadableDatabase();
              String sql = "SELECT name from WordbookInfo";
             Cursor cursor = db.rawQuery(sql, null);
            while(cursor.moveToNext())
            {
                String name = cursor.getString(0);
                 wordbooks.add(name);

            }
            adapter =   new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, wordbooks);
            listWordbooks.setAdapter(adapter);
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();


        } catch (Exception e) {
            System.out.println("에러 "+e.toString());
            Log.d("StartActivityyyy", "error in init : " + e.toString());
        }
    }
    /* End of InitList */





    /* MainActivity와 통신하기 위한 interface
    *  MainActivity가 이 인터페이스를 구현해야만 한다. */
    public interface AccidentListener{
        void showWordbook(String wordbookName);
   }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // Activity(MainActivity)가 onSelectedListener를 구현했는지 확인
        try{
            mCallback = (AccidentListener) activity;
        } catch(ClassCastException e){
            System.out.println("에러 ? : "+e.toString());
            Log.d("GUN", activity.toString() + "must implement AccidentListner");
        }
    }

}
