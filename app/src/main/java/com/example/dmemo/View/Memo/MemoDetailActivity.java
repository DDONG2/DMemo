package com.example.dmemo.View.Memo;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmemo.Utils.VoiceInputDialog;
import com.example.dmemo.View.Main.MainActivity;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;
import com.example.dmemo.View.Main.MainPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemoDetailActivity extends AppCompatActivity implements MemoDetailContract.View {


    @BindView(R.id.tv_detail_title)
    EditText et_detail_title;

    @BindView(R.id.tv_detail_content)
    EditText et_detail_content;

    @BindView(R.id.tv_detail_date)
    TextView tv_detail_date;

    @BindView(R.id.sv_detail_scroll)
    ScrollView sv_detail_scroll;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.btn_add_memo_voice)
    Button btn_add_memo_voice;

    private int id;
    private String title;
    private String content;
    private String date;

    /**
     * 메모 디테일 프레젠터
     */
    private MemoDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memo_detail_list);
        ButterKnife.bind(this);

        presenter = new MemoDetailPresenter(this);
        presenter.onStartPresenter();

        id = getIntent().getIntExtra("_id", 0);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        date = getIntent().getStringExtra("date");

        et_detail_title.setText(title);
        et_detail_content.setText(content);
        tv_detail_date.setText(date);

        touchListener();
    }

    @OnClick({R.id.btn_submit})
    @Override
    public void onClickSubmitButton() {

        btn_submit.setVisibility(View.VISIBLE);

        //callDATA 메모 리스트 저장 프레젠터
        presenter.callSubmitDATA(et_detail_title.getText(), et_detail_content.getText(), id);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    @OnClick({R.id.btn_add_memo_voice})
    @Override
    public void onClickVoiceButton() {
        InputMethodManager immDown = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // 키보드 숨기기
        immDown.hideSoftInputFromWindow(et_detail_title.getWindowToken(), 0);
        immDown.hideSoftInputFromWindow(et_detail_content.getWindowToken(), 0);
        VoiceInputDialog pop = new VoiceInputDialog(MemoDetailActivity.this);
        pop.setDialogListener(new VoiceInputDialog.CustomDialogListener() {
            @Override
            public void onPositiveClicked(String text) {

                String getEdit = et_detail_content.getText().toString();
                if (getEdit.getBytes().length <= 0) {
                    et_detail_content.setText(text);
                } else {
                    et_detail_content.setText(getEdit + " " + text);
                }
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "다시 말씀 해 주세요!", Toast.LENGTH_SHORT).show();
            }
        });


        pop.show();
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

    public void touchListener() {
        et_detail_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //to do
                    btn_submit.setVisibility(View.VISIBLE);
                    btn_add_memo_voice.setVisibility(View.VISIBLE);
                }
            }
        });
        et_detail_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //to do
                    btn_submit.setVisibility(View.VISIBLE);
                    btn_add_memo_voice.setVisibility(View.VISIBLE);
                }
            }
        });


    }


}
