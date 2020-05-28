package com.example.dmemo.View.Main;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;

public interface MainContract {
    interface View extends BaseView {

        /**
         * 메모 추가 클릭
         */
        void onClickAddMemo();


    }

    interface Presenter extends BasePresenter {

        //void callMainInfoAPI(String cityCd, String langSelCd);


    }


}
