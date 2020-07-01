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

import com.example.dmemo.Utils.VoiceInputDialog;
import com.example.dmemo.View.Main.MainActivity;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;

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

    @Override
    public void onClickSave() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memo_detail_list);
        ButterKnife.bind(this);

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
    public void onClickSubmitButton() {

        long now = System.currentTimeMillis();
        Date dateNow = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");

        String date = sdf.format(dateNow);

        btn_submit.setVisibility(View.VISIBLE);
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("UPDATE mytable SET title = '" + et_detail_title.getText() + "', content = '" + et_detail_content.getText() + "', date = '" + date + "' where _id = '" + id + "';");
        db.close();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);      //이렇게 하면 앱 테스크를 모두 지울 수 있다. (원래는 Activity 가 스택에 쌓이는데 그걸 클리어해준다!)
        startActivity(intent);
        finish();
    }


    @OnClick({R.id.btn_add_memo_voice})
    public void onClickVoiceButton() {
        InputMethodManager immDown = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // 키보드 숨기기
        immDown.hideSoftInputFromWindow(et_detail_title.getWindowToken(), 0);
        immDown.hideSoftInputFromWindow(et_detail_content.getWindowToken(), 0);
        VoiceInputDialog pop = new VoiceInputDialog(MemoDetailActivity.this);
        pop.setDialogListener(new VoiceInputDialog.CustomDialogListener() {
            @Override
            public void onPositiveClicked(String text) {

                String getEdit = et_detail_content.getText().toString();
                if(getEdit.getBytes().length <= 0) {
                    et_detail_content.setText(text);
                } else{
                    et_detail_content.setText(getEdit + " " + text);
                }
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
