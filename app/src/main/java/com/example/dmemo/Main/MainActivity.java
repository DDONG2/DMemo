package com.example.dmemo.Main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.dmemo.Adapter.MemoAdapter;
import com.example.dmemo.R;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements MainContract.View {




    private MemoAdapter adapter;

    private ArrayList<memoListDTO> memoList = new ArrayList<memoListDTO>();

    @BindView(R.id.rc_memo_list)
    RecyclerView rcMemoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2020.02.18 테스트업로드!
        adapter = new MemoAdapter();
        rcMemoList.setAdapter(adapter);


        //memoList = ?
        adapter.setFeedList(memoList);

    }


    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClickClose() {

    }
}
