package com.example.dmemo.Main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmemo.Adapter.MemoAdapter;
import com.example.dmemo.Memo.AddMemoActivity;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {


    private MemoAdapter adapter;

    private ArrayList<memoListDTO> memoList = new ArrayList<memoListDTO>();


    @BindView(R.id.btn_add_memo)
    Button btn_add_memo;

    @BindView(R.id.rc_memo_list)
    RecyclerView rcMemoList;

    @BindView(R.id.tv_empty)
    TextView tv_empty;

    @BindView(R.id.tv_list_conut)
    TextView tv_list_conut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void initView() {
        memoList.clear();
        DBHelper helper = new DBHelper(this);
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
        }
        db.close();

        if (memoList != null && memoList.size() > 0) {
            tv_list_conut.setText(String.valueOf(memoList.size()));
            adapter = new MemoAdapter();
            adapter.setFeedList(memoList);
            rcMemoList.setAdapter(adapter);

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rcMemoList.setLayoutManager(manager);

            rcMemoList.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.GONE);

        } else {
            rcMemoList.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
            tv_list_conut.setText("0");
        }
    }

    @Override
    public void onClickClose() {

    }


    @OnClick(R.id.btn_add_memo)
    @Override
    public void onClickAddMemo() {
        Intent intent = new Intent(MainActivity.this, AddMemoActivity.class);
        startActivity(intent);
    }
}
