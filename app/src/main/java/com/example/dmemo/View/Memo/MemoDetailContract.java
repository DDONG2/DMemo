package com.example.dmemo.View.Memo;

import android.text.Editable;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;

public interface MemoDetailContract {

    interface View extends BaseView {

        /**
         * 메모 저장 클릭
         */
        void onClickDetailSubmitButton();

        /**
         * 메모 음성 작성 클릭
         */
        void onClickDetailVoiceButton();
    }

    interface Presenter extends BasePresenter {
        /**
         * 메모 수정 후 저장
         */
        boolean callDetailSubmitDATA(String memoTitle, String memoContent, int memoId);


    }

}
