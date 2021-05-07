package com.example.dmemo.View.Memo;

import android.text.Editable;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;

public interface MemoDetailContract {

    interface View extends BaseView {

        /**
         * 메모 저장 클릭
         */
        void onClickSubmitButton();

        /**
         * 메모 음성 작성 클릭
         */
        void onClickVoiceButton();
    }

    interface Presenter extends BasePresenter {
        /**
         * 메모 저장
         */
        boolean callSubmitDATA(Editable memoTitle, Editable memoContent, int memoId);


    }

}
