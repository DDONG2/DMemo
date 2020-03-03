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




    @BindView(R.id.et_detail_title)
    TextView et_detail_title;

    @BindView(R.id.et_detail_content)
    TextView et_detail_content;



    @Override
    public void onClickSave() {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_memo);
        ButterKnife.bind(this);

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
