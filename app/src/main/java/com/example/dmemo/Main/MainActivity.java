package com.example.dmemo.Main;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dmemo.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //2020.02.18 테스트업로드!
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
