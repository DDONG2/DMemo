package com.example.dmemo.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmemo.R;
import com.example.dmemo.View.Memo.AddMemoActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//참고 URL
//https://happythingsmaker.wordpress.com/2017/10/22/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C
// -%EC%9D%8C%EC%84%B1%EC%9D%B8%EC%8B%9D-textview-%EC%97%90-%EB%82%B4-%EB%AA%A9%EC%86%8C%EB%A6%AC%EB%A5%BC-%EB%8B%B4%EB%8A%94-%EC%98%88%EC%A0%9C-%EC%86%8C/

public class VoiceInputDialog extends Dialog {

    @BindView(R.id.search_voice_tv_status)
    TextView Search_voice_tv_status;

    @BindView(R.id.search_voice_iv_close)
    ImageView Search_voice_iv_close;


    @BindView(R.id.iv_loding_view)
    ImageView iv_loding_view;

    private Animation anim_Loding;

    Intent intent;

    SpeechRecognizer mRecognizer;

    private boolean secondResult = true;

    private Context mContext;

    private CustomDialogListener customDialogListener;

    //인터페이스 설정
    public interface CustomDialogListener{
        void onPositiveClicked(String text);

        void onError();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }



    public VoiceInputDialog(Context context) {
        super(context, R.style.popup);

        setContentView(R.layout.popup_search_voice);

        mContext = context;

        initView();

        mRecognizer.startListening(intent);

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    void initView() {
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");


        mRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
        mRecognizer.setRecognitionListener(recognitionListener);

    }


    @OnClick(R.id.search_voice_iv_close)
    public void onClickVoice() {
        mRecognizer.destroy();
        dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        anim_Loding = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
        iv_loding_view.setAnimation(anim_Loding);
    }

    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float v) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int i) {
            customDialogListener.onError();
            dismiss();
        }

        @Override
        public void onResults(Bundle bundle) {
            if(secondResult) {
                String key = "";
                key = SpeechRecognizer.RESULTS_RECOGNITION;
                ArrayList<String> mResult = bundle.getStringArrayList(key);

                String[] rs = new String[mResult.size()];
                mResult.toArray(rs);

                //  et_content.setText(rs[0]); // TODO: 콜백메소드 구현해서 던져줘야함
                customDialogListener.onPositiveClicked(rs[0]);
                secondResult = false;
                dismiss();

            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
        }
    };

}

