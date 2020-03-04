package com.example.dmemo.Memo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoDetailActivity extends AppCompatActivity implements MemoDetailContract.View {




    @BindView(R.id.tv_detail_title)
    TextView et_detail_title;

    @BindView(R.id.tv_detail_content)
    TextView et_detail_content;

    @BindView(R.id.tv_detail_date)
    TextView tv_detail_date;

    private String title;
    private String content;
    private String date;

    @Override
    public void onClickSave() {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memo_detail_list);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        date = getIntent().getStringExtra("date");

        et_detail_title.setText(title);
        et_detail_content.setText(content);
        tv_detail_date.setText(date);
    }



    @Override
    public Context getActivityContext() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClickClose() {

    }
}
