package com.example.dmemo.View.Memo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmemo.Utils.VoiceInputDialog;
import com.example.dmemo.View.Main.MainActivity;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMemoActivity extends AppCompatActivity implements AddMemoContract.View {


    @BindView(R.id.ll_top_bar)
    LinearLayout ll_top_bar;

    @BindView(R.id.ll_memo_set)
    LinearLayout ll_memo_set;

    @BindView(R.id.et_title)
    EditText et_title;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.btn_add_memo_submit)
    Button btn_add_memo_submit;

    @BindView(R.id.btn_add_memo_voice)
    Button btn_add_memo_voice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_memo);
        ButterKnife.bind(this);


        // Edit Text 포커스 주고 키보드 올리기
        et_title.requestFocus();
        InputMethodManager immUp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        immUp.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


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

    @OnClick(R.id.btn_add_memo_submit)
    @Override
    public void onClickSave() {

        String title = et_title.getText().toString();
        String content = et_content.getText().toString();

        long now = System.currentTimeMillis();
        Date dateNow = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");

        String date = sdf.format(dateNow);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into mytable (title, content, date) values (?,?,?)",
                new String[]{title, content, date});
        db.close();

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // 키보드 숨기기
        imm.hideSoftInputFromWindow(et_title.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);      //이렇게 하면 앱 테스크를 모두 지울 수 있다. (원래는 Activity 가 스택에 쌓이는데 그걸 클리어해준다!)
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.btn_add_memo_voice)
    @Override
    public void onClickVoice() {
        InputMethodManager immDown = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // 키보드 숨기기
        immDown.hideSoftInputFromWindow(et_title.getWindowToken(), 0);
        immDown.hideSoftInputFromWindow(et_content.getWindowToken(), 0);
        VoiceInputDialog pop = new VoiceInputDialog(AddMemoActivity.this);
        pop.setDialogListener(new VoiceInputDialog.CustomDialogListener() {
            @Override
            public void onPositiveClicked(String text) {
                et_content.setText(text);
            }
        });


        pop.show();
    }

    interface CustomDialogListener{
        void onPositiveClicked(String name, String age, String addr);
        void onNegativeClicked();
    }



}
