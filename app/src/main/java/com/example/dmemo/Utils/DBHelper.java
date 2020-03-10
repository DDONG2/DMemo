package com.example.dmemo.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // 안드로이드에서 SQLite 데이터 베이스를 쉽게 사용할 수 있도록 도와주는 클래스
    public DBHelper(Context context) {
        super(context, "memodb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초에 데이터베이스가 없을경우, 데이터베이스 생성을 위해 호출됨
        // 테이블 생성하는 코드를 작성한다
        //onCreate() 함수는 앱 설치 후 최초 한 번만 호출되므로 수정한 부분이 반영이 되지는 않습니다.
//       String memosql = "create table mytable "+"(_id integer primary key autoincrement," + "title"+ "content, "+ "date)";
        String memosql = "create table mytable "+"(_id integer primary key autoincrement,"+" id,"+" title,"+ "content, "+ "date, "+ "imagepath)";

        db.execSQL(memosql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        if(newVersion == 1)
            db.execSQL("drop table mytable");
        onCreate(db); // 다시 테이블 생성
    }
}
