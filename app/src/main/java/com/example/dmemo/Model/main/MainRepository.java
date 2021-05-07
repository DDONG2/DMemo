package com.example.dmemo.Model.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.dmemo.Model.BaseRepository;
import com.example.dmemo.Utils.DBHelper;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

public class MainRepository extends BaseRepository {


    private ArrayList<memoListDTO> memoList = new ArrayList<memoListDTO>();


    private Context context;


    public MainRepository(Context context) {
        this.context = context;


    }

    public ArrayList<memoListDTO> callMainInfoDATA() {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select _id, title, content, date, imagepath from mytable" + "         order by _id desc", null);
        //결국 cursor 에 select한 값이 들어온다!!
        while (cursor.moveToNext()) {
            memoListDTO memo = new memoListDTO();
            memo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
            memo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            memo.setContent(cursor.getString(cursor.getColumnIndex("content")));
            memo.setDate(cursor.getString(cursor.getColumnIndex("date")));
            //memo.setImagePath(cursor.getString(cursor.getColumnIndex("imagepath")));
            memoList.add(memo);
            //checkAll.add(Integer.toString(memo.getId()));  // 전체선택 리스트를 넣어둔다.
        }
        db.close();
        return memoList;
    }

    public boolean callMainDeleteDATA(boolean isCheck, ArrayList<memoListDTO> allMemoList, ArrayList<String> checkOneMemoList) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (isCheck) { //전체선택이 되어있을땐 모든 데이터를 지운다.
            for (int i = 0; i < allMemoList.size(); i++) {
                String sql = "delete from  mytable where _id = '" + allMemoList.get(i).getId() + "'";
                db.execSQL(sql);
            }
        } else {
            for (int i = 0; i < checkOneMemoList.size(); i++) {  //전체선택이 아닐경우 선택된 데이터만 지운다.
                String sql = "delete from  mytable where _id = '" + checkOneMemoList.get(i) + "'";
                db.execSQL(sql);
            }
        }

        return true;
    }


}
