package com.example.dmemo.View.Memo;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;

public interface AddMemoContract {
    interface View extends BaseView {

        /**
         * 메모 저장 클릭
         */
        void onClickSave();

        /**
         * 음성 인식 클릭
         */
        void onClickVoice();

    }

    interface Presenter extends BasePresenter {

        //void callMainInfoAPI(String cityCd, String langSelCd);


    }
}
