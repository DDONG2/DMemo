package com.example.dmemo.View.Memo;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;

public interface AddMemoContract {
    interface View extends BaseView {

        /**
         * 메모 저장 클릭
         */
        void onClickAddSubmitButton();

        /**
         * 메모 음성 작성 클릭
         */
        void onClickAddVoiceButton();

    }

    interface Presenter extends BasePresenter {
        /**
         * 메모 저장
         */
        boolean callDetailAddDATA(String cityCd, String langSelCd);


    }
}
