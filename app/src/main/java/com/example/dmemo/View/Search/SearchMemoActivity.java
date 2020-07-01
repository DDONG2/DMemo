package com.example.dmemo.View.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.dmemo.R;
import com.example.dmemo.View.Main.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchMemoActivity extends AppCompatActivity implements SearchMemoContract.View {

    private SearchMemoPresenter presenter;


    @BindView(R.id.et_search)
    EditText et_search;

    private TextWatcher memoWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_memo);

        ButterKnife.bind(this);

        presenter = new SearchMemoPresenter(this);
      //  presenter.onStartPresenter();
        initView();
    }

    @Override
    public Context getActivityContext() {
        return null;
    }

    @Override
    public void initView() {

        et_search.addTextChangedListener(memoWatcher);



    }

    @Override
    public void onClickClose() {

    }

}
