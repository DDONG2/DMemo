package com.example.dmemo.Model.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;

import com.example.dmemo.Model.BaseRepository;
import com.example.dmemo.Utils.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMemoRepository extends BaseRepository {




    private Context context;


    public AddMemoRepository(Context context) {
        this.context = context;

    }

    public boolean callDetailAddDATA(String memoTitle, String memoContent) {

        long now = System.currentTimeMillis();
        Date dateNow = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");

        String date = sdf.format(dateNow);

        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into mytable (title, content, date) values (?,?,?)",
                new String[]{memoTitle, memoContent, date});
        db.close();

        return true;
    }




}
